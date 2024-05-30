//
//  ViewModelFactory.swift
//  iosApp
//
//  Created by Shubham Tomar on 19/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

protocol ViewModelFactory {
   
    func makeHomeViewModel() -> HomeIOSViewModel
}
 
class ViewModelFactoryImpl: ViewModelFactory {
    let sharedViewModelProvider : SharedViewModelProvider
    let coordinatorFactory : CoordinatorFactory
    
    init(sharedViewModelProvider: SharedViewModelProvider, coordinatorFactory : CoordinatorFactory) {
        self.sharedViewModelProvider = sharedViewModelProvider
        self.coordinatorFactory = coordinatorFactory
    }
    
     
    func makeHomeViewModel() -> HomeIOSViewModel {
        HomeIOSViewModel(sharedViewModel: sharedViewModelProvider.getHomeViewModel())
    }
    
}

