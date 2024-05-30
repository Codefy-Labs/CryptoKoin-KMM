//
//  HomeCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 22/05/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


class HomeCoordinator : Coordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
     
}


