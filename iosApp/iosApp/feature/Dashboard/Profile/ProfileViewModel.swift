//
//  ProfileViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

class ProfileViewModel : ObservableObject {
    
    let coordinator : ProfileCoordinator
    private let vmShared : ProfileSharedVM
    
    
    @Published
    var state: ProfileViewState = ProfileViewState.companion.initial()
    private var disposableHandle : DisposableHandle?
    
    
    init(coordinator: ProfileCoordinator, vmShared: ProfileSharedVM) {
        self.coordinator = coordinator
        self.vmShared = vmShared
        observeEvents()
    }
    
    
    func observe(){
        self.disposableHandle = vmShared.state.subscribe(onCollect: { newState in
            DispatchQueue.main.async{
                if let state = newState {
                    withAnimation{
                        self.state = state
                    }
                }
            }
        })
    }
    
    
    func logout(){
        vmShared.logout()
    }
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let success as ProfileEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as ProfileEvent.Error :
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
