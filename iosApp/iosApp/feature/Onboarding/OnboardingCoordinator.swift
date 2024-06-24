//
//  OnboardingCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

protocol OnboardingCoordinator : Coordinator {
    func navigateUp()
}


class OnboardingCoordinatorImpl : OnboardingCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
    
    func navigateUp() {
        navigator.pop()
    }
}


