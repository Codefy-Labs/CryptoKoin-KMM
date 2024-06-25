//
//  ProfileCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


protocol ProfileCoordinator : Coordinator {
    func showLogin()
}


class ProfileCoordinatorImpl : ProfileCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
    
    func showLogin() {
        navigator.push(.login)
    }
     
}


