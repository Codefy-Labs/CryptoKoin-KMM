//
//  Coordinator.swift
//  iosApp
//
//  Created by Shubham Tomar on 19/02/24.
//  Copyright © 2024 orgName. All rights reserved.


import Foundation
import SwiftUI


class Navigator : ObservableObject {
     
    @Published var rootView : Screen = .splashScreen
    
    @Published var rootPath = NavigationPath()
   
    @Published var sheet : Sheet?
    @Published var fullScreenCover : FullScreenCover?
    @Published var currentTabPosition = 0
    
    func push(_ screen : Screen){
        rootPath.append(screen)
    }
    
    func present(sheet : Sheet){
        self.sheet = sheet
    }
    
    func present(fullScreenCover : FullScreenCover){
        self.fullScreenCover = fullScreenCover
    }
    
    func pop(){
        if rootPath.count > 0 {
            rootPath.removeLast()
        }
       
    }
    
    func popToRoot(){
        if rootPath.count > 0 {
            rootPath.removeLast(rootPath.count)
        }
    }
    
    func dismissSheet(){
        self.sheet = nil
    }
    
    func dismissFullScreenCover(){
        self.fullScreenCover = nil
    }
    
    func replaceRootView(_ screen : Screen){
        self.rootView = screen
    }
    
    
}
