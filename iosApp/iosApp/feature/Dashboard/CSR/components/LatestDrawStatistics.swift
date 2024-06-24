//
//  LatestDrawStatistics.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct ExpressEntryLatestDrawView: View {
    var body: some View {
        VStack(spacing: 16) {
            Text("Canada Express Entry Latest Draw")
                .customFont(18, weight: .medium)
            
            HStack(spacing: 16) {
                ExpressEntryCard(value: "522", title: "Latest CRS Requirement", date: "14 March, 24", isHighlighted: false)
                ExpressEntryCard(value: "522", title: "Number of ITA in Latest Draw", date: "14 March, 24", isHighlighted: false)
                ExpressEntryCard(value: "38,970", title: "Latest CRS ITA issued in", date: "14 March, 24", isHighlighted: true)
            }
        }
        .padding()
    }
}


struct ExpressEntryCard: View {
    let value: String
    let title: String
    let date: String
    let isHighlighted: Bool
    
    var body: some View {
        VStack(spacing: 8) {
            Text(value)
                .customFont( 24, weight: .bold)
                .foregroundColor(isHighlighted ? .red : .primary)
                
            Text(title)
                .customFont( 12)
                .multilineTextAlignment(.center)
                .foregroundColor(.primary)
                .lineLimit(3)
                
            Text(date)
                .font(.system(size: 12))
                .foregroundColor(.secondary)
        }
        .padding()
        .background(Color(UIColor.systemGray6))
        .cornerRadius(8)
    }
}


struct ExpressEntryLatestDrawView_Previews: PreviewProvider {
    static var previews: some View {
        ExpressEntryLatestDrawView()
    }
}
