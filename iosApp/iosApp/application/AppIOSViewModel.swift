//
//  AppViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 05/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared



class AppIOSViewModel : ObservableObject {
     
     
     private var appViewModel = SharedViewModelProvider.shared.getAppViewModel()
       
     var disposableHandle : DisposableHandle?
     @Published var state = AppState.companion.doInit()
      
     init(){
         appViewModel.event.subscribe(onCollect: { e in
             
         })
     }
      
     func observe(){
         self.disposableHandle = appViewModel.state.subscribe(onCollect: { newState in
             if let state = newState {
                 self.state = state
             }
          })
          
     }
     
    deinit{
         disposableHandle?.dispose()
     }
      
 }
 

