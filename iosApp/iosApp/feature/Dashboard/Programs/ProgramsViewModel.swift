//
//  ProgramsViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class ProgramsViewModel : ObservableObject {
    
    private let coordinator : ProgramsCoordinator
    private let vmShared : ProgramsSharedVM
    
    @Published
    var state: ProgramsViewState = ProgramsViewState.companion.initial()
    private var disposableHandle : DisposableHandle?
    
    init(coordinator: ProgramsCoordinator, vmShared: ProgramsSharedVM) {
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
                case let success as ProgramsEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as ProgramsEvent.Error :
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
