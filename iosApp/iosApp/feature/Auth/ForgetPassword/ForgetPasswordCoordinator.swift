//
//  ForgetPasswordCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 26/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

import Foundation


protocol ForgetPasswordCoordinator : Coordinator {
    func navigateUp()
}

class ForgetPasswordCoordinatorImpl : ForgetPasswordCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
      
    func navigateUp() {
        navigator.pop()
    }
     
}
