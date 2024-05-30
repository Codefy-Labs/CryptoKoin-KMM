//
//  NetworkMonitor.swift
//  iosApp
//
//  Created by Shubham Tomar on 25/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Network

class NetworkMonitor: ObservableObject {
    static let shared = NetworkMonitor()
    
    let monitor = NWPathMonitor()
    let queue = DispatchQueue(label: "NetworkMonitor")

    @Published var isConnected: Bool = true

    private init() {
        monitor.pathUpdateHandler = { path in
            DispatchQueue.main.async {
                self.isConnected = path.status == .satisfied
            }
        }
        monitor.start(queue: queue)
    }
}

