//
//  ForgetPasswordViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 26/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//
 


import Foundation
import shared
import SwiftUI


class ForgetPasswordViewModel : ObservableObject {
     
    private let vmShared : ForgetPasswordSharedVM
    let coordinator : ForgetPasswordCoordinator
    
    var disposableHandle : DisposableHandle?
    
   
    @Published var state = ForgetPasswordState.companion.doInit()
    
    init(sharedForgetPasswordViewModel : ForgetPasswordSharedVM, coordinator : ForgetPasswordCoordinator){
        self.vmShared = sharedForgetPasswordViewModel
        self.coordinator = coordinator
        observeEvents()
    }
    
    private func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            DispatchQueue.main.async{
                if let event = e {
                    switch event {
                    case let otpSent as ForgetPasswordEvent.OtpSent:
                        ToastManager.shared.success(message:  otpSent.message)
                        
                    case let passwordResetSuccess as ForgetPasswordEvent.PasswordResetSuccess:
                        ToastManager.shared.success(message:  passwordResetSuccess.message)
                        self.coordinator.navigateUp()
                        
                    case let error as ForgetPasswordEvent.Error:
                        ToastManager.shared.show(toast: Toast(style: .error, message: error.error))
                    default: break
                    }
                }
            }
        })
    }
    
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
    
    func onChangeEmail(_ value : String){
        vmShared.onEmailChanged(email: value)
    }
    
    func onChangeOtp(_ value : String){
        vmShared.onOtpChanged(otp: value)
    }
    
    func onChangePassword(_ value : String){
        vmShared.onPasswordChanged(password: value)
    }
    
    func handleSubmit() {
        vmShared.handleSubmit()
    }
    
    func editEmail(){
        vmShared.editEmail()
    }
    
    
    deinit{
        disposableHandle?.dispose()
    }
    
    
}


