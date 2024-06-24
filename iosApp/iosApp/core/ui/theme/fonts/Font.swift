//
//  Font.swift
//  iosApp
//
//  Created by Shubham Tomar on 02/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

enum CustomFontWeight {
    case light
    case regular
    case medium
    case semiBold
    case bold
    
    var fontName: String {
        switch self {
        case .regular: return "OpenSans-Regular"
        case .bold: return "OpenSans-Bold"
        case .medium: return "OpenSans-Medium"
        case .light:  return "OpenSans-Light"
        case .semiBold:  return "OpenSans-SemiBold"
        }
    }
}

extension View {
    func customFont(_ size: CGFloat, weight: CustomFontWeight = .regular) -> some View {
        self.modifier(CustomFontModifier(name: weight.fontName, size: size))
    }
}

struct CustomFontModifier: ViewModifier {
    let name: String
    let size: CGFloat
    
    func body(content: Content) -> some View {
        content.font(.custom(name, size: size))
    }
}
