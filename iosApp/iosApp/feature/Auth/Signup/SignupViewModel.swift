//
//  SignupViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class SignupViewModel : ObservableObject {
    
    
    let coordinator : SignupCoordinator
    private let vmShared : SignUpSharedVM
    
    init(coordinator: SignupCoordinator, vmShared: SignUpSharedVM) {
        self.coordinator = coordinator
        self.vmShared = vmShared
        observeEvents()
    }
    
    @Published
    var state: SignupViewState = SignupViewState.companion.emptyState()
    private var disposableHandle : DisposableHandle?
     
    func observe(){
        self.disposableHandle = vmShared.state.subscribe(onCollect: { newState in
            DispatchQueue.main.async{
                if let state = newState {
                    self.state = state
                }
            }
        })
    }
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let sucess as SignupViewEvent.Success :
                    self.coordinator.navigateUp()
                    
                case let error as SignupViewEvent.Error :
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
