//
//  Theme.swift
//  iosApp
//
//  Created by Shubham Tomar on 02/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct AppTheme {
    static let primaryColor = Color.blue
    static let secondaryColor = Color.gray
    static let accentColor = Color.pink
    static let dangerColor = Color.red
    
    // Ensure the font is loaded in your project for this to work
    static let buttonFont = Font.custom("DMSans-Regular", size: 16)
    static let buttonTextColor = Color.white
    static let buttonCornerRadius: CGFloat = 8
    static let buttonPadding: CGFloat = 10
    static let buttonBorderWidth: CGFloat = 1
}
