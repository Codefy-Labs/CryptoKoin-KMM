//
//  ProcessViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class ProcessViewModel : ObservableObject {
    
    private let coordinator : ProcessCoordinator
    private let vmShared : ProcessSharedVM
    
    @Published
    var state: ProcessViewState = ProcessViewState.companion.initial()
    private var disposableHandle : DisposableHandle?
    
    init(coordinator: ProcessCoordinator, vmShared: ProcessSharedVM) {
        self.coordinator = coordinator
        self.vmShared = vmShared
        observeEvents()
    }
    
    func observe(){
        self.disposableHandle = vmShared.state.subscribe(onCollect: { newState in
            DispatchQueue.main.async{
                if let state = newState {
                    self.state = state
                }
            }
        })
    }
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let success as ProcessEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as ProcessEvent.Error :
                    ToastManager.shared.error(message: error.error)
                default: break
                }
            }
        })
    }
    
    deinit{
        disposableHandle?.dispose()
    }
}
