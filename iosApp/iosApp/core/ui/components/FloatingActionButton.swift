//
//  FloatingActionButton.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct FloatingActionButton: View {
    let imgSystemName : String
    
    var body: some View {
        Image(systemName:imgSystemName)
            .resizable()
            .frame(width: 24, height: 24)
            .padding()
            .background(Color.accentColor)
            .foregroundColor(.white)
            .clipShape(Circle())
            .shadow(radius: 10)
    }
}

struct FloatingActionExtendButton: View {
    let isExpanded: Bool
    let text : String
    let imgSystemName : String
    let onClick : () -> Void
    
    var body: some View {
        HStack {
            Image(systemName: imgSystemName)
                .resizable()
                .frame(width: 24, height: 24)
                .foregroundColor(.white)
            
            if isExpanded {
                Text(text)
                    .foregroundColor(.white)
                    .transition(.opacity)
            }
        }
        .padding()
        .background(Color.accentColor)
        .onTapGesture {
            onClick()
        }
        .cornerRadius(30)
        .shadow(radius: 10)
        .animation(.spring(), value: isExpanded)
    }
}

#Preview {
    VStack{
        FloatingActionButton(imgSystemName: "plus")
        FloatingActionExtendButton(isExpanded: true, text: "Discussion", imgSystemName: "bubble.left.and.bubble.right"){}
        FloatingActionExtendButton(isExpanded: false, text: "Discussion", imgSystemName: "bubble.left.and.bubble.right"){}
    }
}
