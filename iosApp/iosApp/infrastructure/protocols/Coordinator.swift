//
//  Coordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 20/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

protocol Coordinator {
    var navigator: Navigator { get set }
   
    init(navigator: Navigator)
    
}
