//
//  ViewExtension.swift
//  iosApp
//
//  Created by Shubham Tomar on 03/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

 

struct ScrollOffsetPreferenceKey: PreferenceKey {
    static var defaultValue: CGFloat = 0
    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value += nextValue()
    }
}
