//
//  SignupViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

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
     
    func observeState(){
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
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let verificationEmailSent as SignupViewEvent.VerificationEmailSent :
                    ToastManager.shared.success(message: verificationEmailSent.message)
                    self.coordinator.navigateUp()
                    
                case let googleSignUpSuccessful as SignupViewEvent.GoogleSignUpSuccessful :
                    ToastManager.shared.success(message: googleSignUpSuccessful.message)
                    self.coordinator.navigateUpToRoot()
                    
                case let error as SignupViewEvent.Error :
                    ToastManager.shared.error(message: error.error)
                default: break
                }
            }
        })
    }
    
    func onChangeName(_ value : String){
        vmShared.onNameChanged(name: value)
    }
    
    func onChangeEmail(_ value : String){
        vmShared.onEmailChanged(email: value)
    }
    
    func onChangePassword(_ value : String){
        vmShared.onPasswordChanged(password: value)
    }
    
    func onChangeConfirmPassword(_ value : String){
        vmShared.onConfirmPasswordChange(password: value)
    }
    
    func signUp(){
        vmShared.signUp()
    }
    
    func signUpWithGoogle() async   {
        await GoogleSignInHelper.invokeGoogleSignIn(onSuccess: { idToken in
            vmShared.signUpWithGoogle(idToken: idToken)
        }, onError: { error in
            ToastManager.shared.error(message: "\(error)")
        })
    }
    
    deinit{
        disposableHandle?.dispose()
    }
    
    
}
