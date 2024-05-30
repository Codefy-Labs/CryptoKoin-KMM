//
//  AppDelegate.swift
//  iosApp

//  Created by Shubham Tomar on 18/01/24.
//  Copyright © 2024 orgName. All rights reserved.

import Foundation
import shared
import UIKit

class AppDelegate: UIResponder,  UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        startKoin()
        LoggerInitializerKt.initializeLogger()
        return true
    } 
}
