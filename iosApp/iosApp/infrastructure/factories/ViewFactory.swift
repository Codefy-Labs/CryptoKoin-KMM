//
//  ViewFactory.swift
//  iosApp
//
//  Created by Shubham Tomar on 20/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
 
protocol ViewFactory {
    @ViewBuilder
    func build(screen: Screen) -> AnyView
    
    
    @ViewBuilder
    func build(sheet : Sheet) -> AnyView
    
    
    @ViewBuilder
    func build(fullScreenCover : FullScreenCover) -> AnyView
}

class ViewFactoryImp: ViewFactory {
    
    init(viewModelFactory: ViewModelFactory) {
        self.viewModelFactory = viewModelFactory
    }
    
    let viewModelFactory: ViewModelFactory
    
    func build(screen: Screen) -> AnyView {
        switch screen {
            
        case .splashScreen:
            return AnyView(SplashScreen())
          
        case .homeScreen:
            return AnyView(HomeScreen(vm: self.viewModelFactory.makeHomeViewModel()))
        }
        
    }
    
    func build(sheet: Sheet) -> AnyView {
        switch sheet {
            
        case .blankSheet :
            return AnyView(EmptyView())
        }
    }
     
    func build(fullScreenCover: FullScreenCover) -> AnyView {
        switch fullScreenCover {
            
        case .blankCover:
            return AnyView(EmptyView())
            
        }
    }
     
}



