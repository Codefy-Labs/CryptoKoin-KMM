//
//  NetworkMonitor.swift
//  iosApp
//
//  Created by Shubham Tomar on 08/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Network
import UserNotifications

class NetworkStatusNotifier {
    static var shared : NetworkStatusNotifier = NetworkStatusNotifier()
    
    private var monitor: NWPathMonitor?
    
   private init() {
        monitor = NWPathMonitor()
    }
    
    func startMonitoring() {
        guard let monitor = monitor else { return }
        
        monitor.start(queue: DispatchQueue.global(qos: .background))
        monitor.pathUpdateHandler = { [weak self] path in
            if path.status == .satisfied {
                print("Network is available")
            } else {
                print("Network is unailable")
            }
        }
    }
    
    func stopMonitoring() {
        monitor?.cancel()
        monitor = nil
    }
     
}
