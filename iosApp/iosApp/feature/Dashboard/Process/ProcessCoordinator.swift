//
//  ProcessCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

protocol ProcessCoordinator : Coordinator {
    
}


class ProcessCoordinatorImpl : ProcessCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
     
}


