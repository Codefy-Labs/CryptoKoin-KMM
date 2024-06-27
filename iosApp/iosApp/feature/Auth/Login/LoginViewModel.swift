//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import FirebaseCore
import GoogleSignIn
import FirebaseAuth
import SwiftUI

class LoginViewModel : ObservableObject {
    
    let coordinator : LoginCoordinator
    private let vmShared : LoginSharedVM
    
    @Published var googleSigInProcessing  = false
    
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
                    withAnimation{
                        self.state = state
                    }
                }
            }
        })
    }
    
    func onChangePassword(_ pass : String){
        vmShared.onChangePassword(password: pass)
    }
    
    func onChangeEmail(_ mail : String){
        vmShared.onChangeEmailId(email: mail)
    }
    
    func signIn(){
        vmShared.login()
    }
      
    func signInWithGoogle() async   {
        await GoogleSignInHelper.invokeGoogleSignIn(onSuccess: { idToken in
            vmShared.signInWithGoogle(idToken: idToken)
        }, onError: { error in
            ToastManager.shared.error(message: "\(error)")
        })
    }
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let navigateToDashboard as LoginEvent.NavigateToDashboard :
                    ToastManager.shared.success(message:  navigateToDashboard.message)
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
