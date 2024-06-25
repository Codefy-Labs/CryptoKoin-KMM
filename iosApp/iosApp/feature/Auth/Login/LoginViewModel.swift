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
    
    private func toggleGoogleSigInProcessing(_ value : Bool){
        DispatchQueue.main.sync {
            if(self.googleSigInProcessing != value ){
                withAnimation{
                    self.googleSigInProcessing.toggle()
                }
            }
        }
    }
  
    func signInWithGoogle() async   {
        self.toggleGoogleSigInProcessing(true)
        guard let clientID = FirebaseApp.app()?.options.clientID else {
            self.googleSigInProcessing = false
            fatalError("No client ID found in Firebase configuration")
        }
         
        let config = GIDConfiguration(clientID: clientID)
        GIDSignIn.sharedInstance.configuration = config
        
        guard let windowScene =  await UIApplication.shared.connectedScenes.first as? UIWindowScene,
              let window =  await windowScene.windows.first,
              let rootViewController =  await window.rootViewController else {
            print("There is no root view controller!")
            self.toggleGoogleSigInProcessing(false)
            return
        }
        
        do {
            let userAuthentication = try await GIDSignIn.sharedInstance.signIn(withPresenting: rootViewController)
             
            let user = userAuthentication.user
            guard let idToken = user.idToken else {
                self.toggleGoogleSigInProcessing(false)
                fatalError( "ID token missing")
            }
            let accessToken = user.accessToken
            
            let credential = GoogleAuthProvider.credential(withIDToken: idToken.tokenString,
                                                           accessToken: accessToken.tokenString)
            
            let result = try await Auth.auth().signIn(with: credential)
            let firebaseUser = result.user
            
            print("User \(firebaseUser.uid) signed in with email \(firebaseUser.email ?? "unknown") and access token \( String(describing: user.accessToken.tokenString))")
             
            let googleUser = GoogleUser(idToken: user.accessToken.tokenString, displayName: user.profile?.name ?? "", profilePicUrl: user.profile?.imageURL(withDimension: 320)?.path())
            self.toggleGoogleSigInProcessing(false)
            vmShared.loginWithGoogle(googleUser: googleUser)
        }
        catch {
            self.toggleGoogleSigInProcessing(false)
            print(error.localizedDescription)
            ToastManager.shared.error(message: "\(error.localizedDescription)")
        }
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
