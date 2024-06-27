//
//  HomeCoordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


protocol HomeCoordinator : Coordinator {
    func showNewsDetail(newsId : String)
}


class HomeCoordinatorImpl : HomeCoordinator {
    var navigator: Navigator
    
    required init(navigator: Navigator) {
        self.navigator = navigator
    }
     
    func showNewsDetail(newsId: String) {
        navigator.push(.newsDetail(newsId: newsId))
    }
}


