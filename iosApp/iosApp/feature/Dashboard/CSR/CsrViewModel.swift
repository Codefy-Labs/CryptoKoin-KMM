//
//  CrsViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class CrsViewModel : ObservableObject {
    
    private let coordinator : CsrCoordinator
    private let vmShared : CrsSharedVM
    
    @Published
    var state: CrsViewState = CrsViewState.companion.initial()
    private var disposableHandle : DisposableHandle?
    
    
    init(coordinator: CsrCoordinator, vmShared: CrsSharedVM) {
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
                case let success as CrsEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as CrsEvent.Error :
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
