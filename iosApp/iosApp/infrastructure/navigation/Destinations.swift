//
//  Screen.swift
//  iosApp
//
//  Created by Shubham Tomar on 20/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum Screen : Identifiable, Hashable {
    
    case splashScreen
    case homeScreen
 
    var id: String {
        switch self {
        case .splashScreen :
            return "splash"
    
        case .homeScreen:
            return "homeScreen"
      
        }
    }
}

enum Sheet :  Identifiable, Hashable {
    case blankSheet;
    
    var id : String {
        switch self{
        case .blankSheet:
            return "blankSheet"
        }
    }
    
}

enum FullScreenCover : String, Identifiable {
    case  blankCover
    
    var id : String {
        self.rawValue
    }
}
 
