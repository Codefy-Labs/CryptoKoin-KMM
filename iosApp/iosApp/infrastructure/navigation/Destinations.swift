//
//  Screen.swift
//  iosApp
//
//  Created by Shubham Tomar on 20/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import Foundation

enum Screen : Identifiable, Hashable {
    
    case splashScreen
    case dashboardScreen
    case homeScreen
    case programsScreen
    case crsScreen
    case profileScreen
    case processScreen
    case onboardingScreen
    case signup
    case login
    case forgetPassword
 
    var id: String {
        switch self {
        case .splashScreen :
            return "splash"
        case .homeScreen:
            return "homeScreen"
        case .programsScreen:
            return "programsScreen"
        case .crsScreen:
            return "crsScreen"
        case .profileScreen:
            return "profileScreen"
        case .processScreen:
            return  "profileScreen"
        case .dashboardScreen:
            return "dashboardScreen"
        case .onboardingScreen:
            return "onboardingScreen"
        case .signup:
            return "signup"
        case .login:
            return "login"
        case .forgetPassword :
            return "forgetPassword"
        }
    }
}

enum Sheet :  Identifiable, Hashable {
    case blankSheet;
    
    var id : String {
        switch self{
        case .blankSheet:
            return "blankSheet"
        }
    }
    
}

enum FullScreenCover : String, Identifiable {
    case  blankCover
    
    var id : String {
        self.rawValue
    }
}
 
