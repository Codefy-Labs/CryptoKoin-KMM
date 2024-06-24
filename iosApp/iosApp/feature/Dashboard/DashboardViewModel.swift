//
//  DashboardViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//
 
import shared
import Foundation

class DashboardViewModel : ObservableObject {
    
    private let coordinator : DashboardCoordinator
    private let vmShared : DashboardSharedVM
    
    @Published var selectedTab = 1
  
    init(coordinator: DashboardCoordinator, vmShared: DashboardSharedVM) {
        self.coordinator = coordinator
        self.vmShared = vmShared
        observeEvents()
    }
    
    func observeEvents(){
        vmShared.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let success as DashboardEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as DashboardEvent.Error :
                    ToastManager.shared.error(message: error.error)
                    
                case let openSurvey as DashboardEvent.OpenOnboardingSurvey :
                    self.coordinator.showSurvey()
                    
                default: break
                }
            }
        })
    }
    
}
