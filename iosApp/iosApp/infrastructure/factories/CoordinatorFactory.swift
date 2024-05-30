//
//  CoordinatorFactory.swift
//  iosApp
//
//  Created by Shubham Tomar on 19/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
 


protocol CoordinatorFactory {
    var navigator: Navigator { get }
     
    func makeHomeCoordinator() -> HomeCoordinator
}
 

class CoordinatorFactoryImpl: CoordinatorFactory {
    
    let navigator: Navigator
    
    init(navigator: Navigator) {
        self.navigator = navigator
    }
      
    func makeHomeCoordinator() -> HomeCoordinator {
        HomeCoordinator  (navigator: navigator)
    }
     
}
