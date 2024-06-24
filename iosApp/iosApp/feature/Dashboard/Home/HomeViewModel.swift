//
//  HomeIOSViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 13/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import Foundation
import SwiftUI

class HomeViewModel : ObservableObject {
    
    private let coordinator : HomeCoordinator
    private let viewModel : HomeSharedViewModel
    private var disposableHandle : DisposableHandle?
    
    @Published
    var state: HomeViewState = HomeViewState.companion.initial()
     
    init(coordinator: HomeCoordinator, viewModel: HomeSharedViewModel) {
        self.coordinator = coordinator
        self.viewModel = viewModel
        observeEvents()
    }
       
    func observe(){
        self.disposableHandle = viewModel.state.subscribe(onCollect: { newState in
            DispatchQueue.main.async{
                if let state = newState {
                    withAnimation{
                        self.state = state
                    }
                }
            }
        })
    }
    
    func observeEvents(){
        viewModel.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let success as HomeEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as HomeEvent.Error :
                    ToastManager.shared.error(message: error.error)
                default: break
                }
            }
        })
    }
    
    func selectFilter(_ filter : String){
        viewModel.selectFilter(filter: filter)
    }
    
    deinit{
        disposableHandle?.dispose()
    }
    
}
