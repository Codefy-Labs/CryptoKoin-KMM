//
//  GoogleSignInHelper.swift
//  iosApp
//
//  Created by Shubham Tomar on 26/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import FirebaseCore
import GoogleSignIn


class GoogleSignInHelper{
    
   static func invokeGoogleSignIn(onSuccess : (String) -> Void, onError : (String) -> Void ) async{
        guard let clientID = FirebaseApp.app()?.options.clientID else {
           fatalError("No client ID found in Firebase configuration")
        }
         
        let config = GIDConfiguration(clientID: clientID)
        GIDSignIn.sharedInstance.configuration = config
        
        guard let windowScene =  await UIApplication.shared.connectedScenes.first as? UIWindowScene,
              let window =  await windowScene.windows.first,
              let rootViewController =  await window.rootViewController else {
            print("There is no root view controller!")
            return
        }
        
        do {
            let userAuthentication = try await GIDSignIn.sharedInstance.signIn(withPresenting: rootViewController)
             
            let user = userAuthentication.user
            guard let idToken = user.idToken else {
                fatalError( "ID token missing")
            }
            
            print("IdToken -> \(idToken.tokenString)")
            onSuccess(idToken.tokenString)
        }
        catch {
            print(error.localizedDescription)
           onError("\(error.localizedDescription)")
        }
    }
}
