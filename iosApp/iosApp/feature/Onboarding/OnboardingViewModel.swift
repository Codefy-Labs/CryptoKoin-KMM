//
//  OnboardingViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class OnboardingViewModel : ObservableObject {
    let coordinator : OnboardingCoordinator
    private let vmShared : OnboardingSharedVM
    
    init(coordinator: OnboardingCoordinator, vmShared: OnboardingSharedVM ) {
        self.coordinator = coordinator
        self.vmShared = vmShared
        observeEvents()
    }
    
    @Published
    var state: OnboardingState = OnboardingState.companion.initial()
    private var disposableHandle : DisposableHandle?
    
    
    @Published var steps: [SurveyContainer] = []
    @Published var currentStep = 0
    
    
    func submit(_ selectedOption : String){
        steps[currentStep].selectedOption = selectedOption
        
        if currentStep < steps.count-1 {
            currentStep += 1
            
            let step = steps[currentStep]
            vmShared.submit(onboardingStep: step.onboardingStep, question: step.description, answer: selectedOption)
            
        } else {
            vmShared.save()
        }
        
    }
    
    func skip(){
        if currentStep < steps.count-1 {
            currentStep += 1
        } else {
            coordinator.navigateUp()
        }
    }
    
    func observe(){
        self.disposableHandle = vmShared.state.subscribe(onCollect: { newState in
            DispatchQueue.main.async{
                if let state = newState {
                    self.state = state
                    
                    if self.steps.isEmpty {
                        self.steps = OnboardingDataProvider.onboardingSteps.filter({ survey in
                            state.onboardingSteps.contains(survey.onboardingStep)
                        })
                    }
                }
            }
        })
    }
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let success as OnboardingEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    self.coordinator.navigateUp()
                    
                case let error as OnboardingEvent.Error :
                    ToastManager.shared.error(message: error.error)
                default: break
                }
            }
        })
    }
    
    deinit{
        disposableHandle?.dispose()
    }
    
}
