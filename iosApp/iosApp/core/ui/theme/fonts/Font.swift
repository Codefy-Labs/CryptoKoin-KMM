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
    case thin
    case extraLight
    case light
    case regular
    case medium
    case semiBold
    case bold
    
    var fontName: String {
        switch self {
        case .regular: return "DMSans-Regular"
        case .bold: return "DMSans-Bold"
        case .medium: return "DMSans-Medium"
        case .thin:  return "DMSans-Thin"
        case .extraLight:  return "DMSans-ExtraLight"
        case .light:  return "DMSans-Liight"
        case .semiBold:  return "DMSans-SemiBold"
        }
    }
}

extension View {
    func customFont(size: CGFloat, weight: CustomFontWeight = .regular) -> some View {
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
