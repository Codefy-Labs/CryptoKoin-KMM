//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class LoginViewModel : ObservableObject {
    
    private let coordinator : LoginCoordinator
    private let vmShared : LoginSharedVM
    
    init(coordinator: LoginCoordinator, vmShared: LoginSharedVM) {
        self.coordinator = coordinator
        self.vmShared = vmShared
        observeEvents()
    }
    
    @Published
    var state: LoginViewState = LoginViewState.companion.initial()
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
                case let navigateToDashboard as LoginEvent.NavigateToDashboard :
                    self.coordinator.showDashboard()
                    
                case let error as LoginEvent.Error :
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
