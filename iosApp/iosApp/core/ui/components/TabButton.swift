//
//  TabButton.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct TabButton: View {
    @Binding var selectedTab: Int
    let index: Int
    let title: String
    
    var body: some View {
        VStack{
            Button(action: {
                selectedTab = index
            }) {
                Text(title)
                    .customFont(  16, weight: selectedTab == index ? .bold : .semiBold)
                    .foregroundColor(selectedTab == index ? Color.primary : Color.primary.opacity(0.5))
                
            }
            
            Rectangle()
                .fill(selectedTab == index ? Color.red : Color.clear)
                .frame(height: 2)
                .padding(.horizontal, 10)
        }
        .padding(10)
    }
}
