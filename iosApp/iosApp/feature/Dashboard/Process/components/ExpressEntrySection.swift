//
//  ExpressEntrySection.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


struct ExpressEntrySection: View {
    let expressEntries : [ExpressEntry] =    [
        ExpressEntry(title: "Overview", imageResId: "ic-search", subtitle: "A Brief overview of the process"),
        ExpressEntry(title: "Pre-Application", imageResId: "ic-application", subtitle: "A Brief overview of the process"),
        ExpressEntry(title: "Pre-ITA", imageResId: "ic-mail", subtitle: "While you wait for your invitation"),
        ExpressEntry(title: "Post-ITA", imageResId: "ic-mail-open", subtitle: "Submitting your application"),
        ExpressEntry(title: "Landing", imageResId: "ic-plane-landing", subtitle: "After you arrive")
    ]
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                ForEach(expressEntries) { expressEntry in
                    ExpressEntryRow(expressEntry: expressEntry)
                }
            }
            .padding()
        }
    }
}

struct ExpressEntry: Identifiable {
    let id = UUID()
    let title: String
    let imageResId: String
    let subtitle: String
}


private struct ExpressEntryRow: View {
    let expressEntry: ExpressEntry
    
    var body: some View {
        VStack {
            Image(expressEntry.imageResId)
                .renderingMode(.template)
                .resizable()
                .scaledToFit()
                .frame(width: 30, height: 30)
                .foregroundColor(.red)
               
            
            Spacer().frame(height: 16)
            
            Text(expressEntry.title)
                .customFont( 14, weight: .semiBold)
                .foregroundColor(.white)
            
            Spacer().frame(height: 4)
            
            Text(expressEntry.subtitle)
                .customFont( 12, weight: .regular)
                .multilineTextAlignment(.center)
                .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .padding()
        .background(Color.black.opacity(0.9))
        .cornerRadius(8)
        .shadow(radius: 4)
        .padding(.horizontal, 6)
    }
}

#Preview {
    ExpressEntrySection()
}
