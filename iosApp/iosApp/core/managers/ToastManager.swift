//
//  ToastManager.swift
//  iosApp
//
//  Created by Shubham Tomar on 30/01/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


class ToastManager : ObservableObject {
    
    static var shared = ToastManager()
    
    @Published var toast: Toast? = nil
    
    func show(toast: Toast){
        self.toast = toast
    }
    
    func dismiss(){
        self.toast = nil
    }
    
    func success(message : String){
        DispatchQueue.main.async {
            self.toast = Toast(style: .success, message: message)
        }
    }
    
    func error(message : String){
        DispatchQueue.main.async {
            self.toast = Toast(style: .error, message: message)
        }
    }
    
    func info(message : String){
        DispatchQueue.main.async {
            self.toast = Toast(style: .info, message: message)
        }
    }
}
