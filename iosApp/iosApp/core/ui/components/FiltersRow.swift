//
//  ChipViewRow.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
  
 
struct FiltersRow: View {
    @Binding var selectedSegment: String
    var segments: [String]

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false, content: {
            HStack {
                Spacer().frame(width: 16)
                ForEach(segments, id: \.self) { segment in
                    Button(action: {
                        selectedSegment = segment
                    }) {
                        Text(segment)
                            .customFont(12, weight: .medium)
                            .padding(.vertical, 6)
                            .padding(.horizontal, 20)
                            .background(selectedSegment == segment ? Color.black : Color.gray.opacity(0.2))
                            .foregroundColor(selectedSegment == segment ? Color.white : Color.black)
                            .cornerRadius(12)
                    }
                    .buttonStyle(PlainButtonStyle())
                }
                Spacer().frame(width: 16)
            }
        })
    }
}

 
// Preview
struct ContentView_Previews: PreviewProvider {
    
    static var previews: some View {
        VStack {
            FiltersRow(selectedSegment: .constant("All"), segments: ["All", "Policy Updates", "Legal Changes", "Success Stories"])
            Spacer()
        }
    }
}
 
