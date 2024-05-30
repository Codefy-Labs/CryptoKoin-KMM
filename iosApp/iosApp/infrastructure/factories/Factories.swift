//
//  Factories.swift
//  iosApp
//
//  Created by Shubham Tomar on 19/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class Factories {
   
    init( viewModelFactory: ViewModelFactory, viewFactory : ViewFactory, navigator : Navigator) {
        self.viewModel = viewModelFactory
        self.viewFactory = viewFactory
        self.navigator = navigator
    }
  
    static var current: Factories = {
          let navigator = Navigator()
        
          let coordinatorFactory = CoordinatorFactoryImpl(navigator: navigator)
          let viewModelFactory = ViewModelFactoryImpl(sharedViewModelProvider: SharedViewModelProvider.shared, 
                                                      coordinatorFactory: coordinatorFactory)
        
          let viewFactory = ViewFactoryImp(viewModelFactory: viewModelFactory)
        
          return Factories( viewModelFactory: viewModelFactory, viewFactory: viewFactory, navigator: navigator)
    }()

    
    let viewModel: ViewModelFactory
    let viewFactory: ViewFactory
    let navigator : Navigator
   
}
