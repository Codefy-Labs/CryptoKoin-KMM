//
//  NewsDetailCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

protocol NewsDetailCoordinator : Coordinator {
    func navigateUp()
    func showDiscussions(id : String)
}


class NewsDetailCoordinatorImpl : NewsDetailCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
    
    func navigateUp() {
        navigator.pop()
    }
    
    func showDiscussions(id : String) {
        navigator.present(sheet: .discussions(id: id))
    }
    
}


