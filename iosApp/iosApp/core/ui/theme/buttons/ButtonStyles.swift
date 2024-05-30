//
//  ButtonStyles.swift
//  iosApp
//
//  Created by Shubham Tomar on 02/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


  
struct ButtonPrimary: ButtonStyle {
    let tint : Color
    
    init(tint: Color = Colors.blueDark) {
        self.tint = tint
    }
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 24)
            .padding(.vertical, 12)
            .customFont(size: 16, weight: .medium)
            .background(tint)
            .foregroundStyle(.white)
            .clipShape(.rect(cornerSize: .init(width: 6, height: 6)))
            .scaleEffect(configuration.isPressed ? 1.2 : 1)
            .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
    }
}

struct ButtonPrimarySmall: ButtonStyle {
    let tint : Color
    
    init(tint: Color = Colors.blueDark) {
        self.tint = tint
    }
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .customFont(size: 14, weight: .medium)
            .background(tint)
            .foregroundStyle(.white)
            .clipShape(.rect(cornerSize: .init(width: 4, height: 4)))
            .scaleEffect(configuration.isPressed ? 1.2 : 1)
            .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
    }
}

struct ButtonOutlined: ButtonStyle {
    let tint : Color
    
    init(tint: Color = Colors.blueDark) {
        self.tint = tint
    }
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 10)
            .padding(.vertical, 8)
            .customFont(size: 16, weight: .medium)
            .foregroundStyle(tint)
            .background(
                RoundedRectangle(
                    cornerRadius: 6,
                    style: .continuous
                ).stroke(tint, lineWidth: 1))
            .scaleEffect(configuration.isPressed ? 1.2 : 1)
            .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
    }
}

struct ButtonOutlinedSmall: ButtonStyle {
    let tint : Color
    
    init(tint: Color = Colors.blueDark) {
        self.tint = tint
    }
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding(.horizontal, 10)
            .padding(.vertical, 6)
            .customFont(size: 12, weight: .medium)
            .foregroundStyle(tint)
            .background(
                RoundedRectangle(
                    cornerRadius: 4,
                    style: .continuous
                ).stroke(tint, lineWidth: 1))
            .scaleEffect(configuration.isPressed ? 1.2 : 1)
            .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
    }
}

struct EffectButtonStyle: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .scaleEffect(configuration.isPressed ? 1.2 : 1)
            .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
    }
}



struct ButtonPrimary_Previews: PreviewProvider {
    static var previews: some View {
        VStack{
            
            
            Button("Press Me") {
                print("Button pressed!")
            }
            .buttonStyle(ButtonPrimary(tint: Colors.blue))
            
            Button("Press Me") {
                print("Button pressed!")
            }
            .buttonStyle(ButtonPrimary())
            
            
            Button("Press Me") {
                print("Button pressed!")
            }
            .buttonStyle(ButtonOutlined()) 
        }
    }
}


