//
//  DeveloperOptionViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 01/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class DeveloperOptionIOSViewModel : ObservableObject {
    
    private let viewModel : SettingViewModel
    
    init(viewModel: SettingViewModel) {
        self.viewModel = viewModel
    }
    
    @Published var state : SettingState = SettingState.companion.initial()
   
    func observeEvent(){
        viewModel.event.subscribe(onCollect: { newEvent in
            if let event = newEvent {
                switch event {
                case let success as SettingEvent.ShowSuccessMessage:
                    ToastManager.shared.show(toast: Toast(style: .success, message: success.message))
                    
                case let error as SettingEvent.Error:
                    ToastManager.shared.show(toast: Toast(style: .error, message: error.error))
                    break
                default: break
                }
            }
            
        })
    }
    
    func observeState(){
        viewModel.state.subscribe(onCollect: { newState in
            if let newState = newState {
                DispatchQueue.main.async {
                    self.state = newState
                }
            }
        })
    }
    
    func toggleAcknowledgeHeartbeat(){
        viewModel.toggleAcknowledgeHeartbeat(completionHandler: {e in})
    }
    
    func toggleAcknowledgeCallback(){
        viewModel.toggleAcknowledgeCallback(completionHandler: {e in})
    }
}
