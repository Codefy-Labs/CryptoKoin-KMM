//
//  LoginCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

protocol LoginCoordinator : Coordinator {
    func showSignup()
    func showForgetPassword()
    func navigateUp()
    func showDashboard()
    
}


class LoginCoordinatorImpl : LoginCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
    
    func showSignup() {
        navigator.push(.signup)
    }
    
    func showForgetPassword() {
        navigator.push(.forgetPassword)
    }
    
    func navigateUp() {
        navigator.pop()
    }
    
    func showDashboard() {
        navigator.popToRoot()
    }
     
}


