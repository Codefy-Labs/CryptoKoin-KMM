//
//  NewsDetailViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class NewsDetailViewModel : ObservableObject {
    
    let coordinator : NewsDetailCoordinator
    private let vmShared : NewsDetailSharedVM
    
    init(coordinator: NewsDetailCoordinator, vmShared: NewsDetailSharedVM) {
        self.coordinator = coordinator
        self.vmShared = vmShared
    }
    
    func showDiscussions(){
        coordinator.showDiscussions(id: "")
    }
}
