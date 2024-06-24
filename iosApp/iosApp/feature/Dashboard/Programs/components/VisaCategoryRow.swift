//
//  VisaCategoryRow.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct VisaCategoryCard: View {
    let category: VisaCategory
    
    var body: some View {
        
        HStack {
            VStack(alignment: .leading) {
                Text(category.title)
                    .customFont( 14, weight: .bold)
                    .foregroundColor(.primary)
                
                Spacer().frame(height: 4)
                
                Text(category.description_)
                    .customFont( 12, weight: .regular)
                    .foregroundColor(.primary)
            }
            
            Spacer()
            
            Image(systemName: "chevron.right")
                .foregroundColor(.red)
        }
        .padding(20)
        .background(Color.gray.opacity(0.2))
        .cornerRadius(8)
        .clipped()
        .padding(.vertical, 2)
    }
}


