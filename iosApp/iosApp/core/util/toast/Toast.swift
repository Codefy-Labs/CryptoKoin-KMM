//
//  Toast.swift
//  iosApp
//
//  Created by Shubham Tomar on 23/01/24.
//  Copyright © 2024 orgName. All rights reserved.


import Foundation

struct Toast: Equatable {
  var style: ToastStyle
  var message: String
  var duration: Double = 3
  var width: Double = .infinity
}
