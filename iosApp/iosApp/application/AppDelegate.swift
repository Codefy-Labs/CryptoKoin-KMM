//
//  AppDelegate.swift
//  iosApp

//  Created by Shubham Tomar on 18/01/24.
//  Copyright © 2024 orgName. All rights reserved.

import Foundation
import shared
import UIKit
import FirebaseCore
import GoogleSignIn

class AppDelegate: UIResponder,  UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        FirebaseApp.configure()
        startKoin()
        LoggerInitializerKt.initializeLogger()
        return true
    } 
    
    func application(_ app: UIApplication,
                     open url: URL,
                     options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
      return GIDSignIn.sharedInstance.handle(url)
    }
   
}
