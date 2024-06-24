//
//  SignupCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
 
protocol SignupCoordinator : Coordinator {
    func navigateUp ()
}


class SignupCoordinatorImpl : SignupCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
    
    func navigateUp() {
        navigator.pop()
    }
 
}


