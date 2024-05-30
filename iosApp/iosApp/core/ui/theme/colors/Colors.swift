//
//  Colors.swift
//  iosApp
//
//  Created by Shubham Tomar on 02/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Colors {
    static let primaryColor = Color(red: 0.4, green: 0.22, blue: 0.85)
    static let actionColor = Color.blue
    static let destructiveColor = Color.red
    static let cancelColor = Color.gray
    
    static let blueDark = Color(hex : "#063A93")
    static let blue = Color(hex : "#0E63F6")
    
    static let greyCaption = Color(hex : "#9A9A9A")
    static let gray100 = Color(hex : "#F5F5F5")
    static let gray400 = Color(hex : "#9A9A9A")
    
    static let greenLight = Color(hex : "#DBF6D5")
    static let green = Color(hex : "#49C92C")
    
    static let redLight = Color(hex : "#FCD2CF")
    static let red = Color(hex : "#F33B2E")
    
    static let blueRadialGradient =  RadialGradient(
        gradient: Gradient(colors: [blue, Color(hex: "#074DC5")]),
        center: .center,
        startRadius: 0,
        endRadius: UIScreen.main.bounds.width
    )
    
    static let blueLienarGradient =   LinearGradient(
        gradient: Gradient(colors: [Color(hex: "#0E63F6"), Color(hex: "#074DC5")]),
        startPoint: .top,
        endPoint: .bottom
    )
    
    
    
}



// Extension to allow initialization of Color with a hex string
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet(charactersIn: "#"))
        let scanner = Scanner(string: hex)
        var rgbValue: UInt64 = 0
        scanner.scanHexInt64(&rgbValue)
        
        let red = Double((rgbValue & 0xFF0000) >> 16) / 255.0
        let green = Double((rgbValue & 0x00FF00) >> 8) / 255.0
        let blue = Double(rgbValue & 0x0000FF) / 255.0
        self.init(red: red, green: green, blue: blue)
    }
}
