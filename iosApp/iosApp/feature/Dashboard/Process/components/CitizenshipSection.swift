//
//  CitizenshipSection.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
 
struct CitizenshipSection: View {
    let citizenshipSteps = [
        "Citizenship Overview",
        "Apply for Citizenship",
        "Complete CIT forms for Citizenship",
        "Receive AOR after Submission",
        "Test date and Interview",
        "Oath Taken & Citizenship Ceremony",
        "Grant of Citizenship"
    ]

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 20) {
                ForEach(citizenshipSteps, id: \.self) { step in
                    RowCardView(step: step)
                }
            }
            .padding(16)
        }
        .navigationTitle("Citizenship Steps")
    }
}

private struct RowCardView: View {
    let step: String

    var body: some View {
        Button(action: {
            // Action on click
        }) {
            HStack {
                Text(step)
                    .customFont( 14, weight: .semiBold)
                    .foregroundColor(.primary)
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .foregroundColor(Color.primary.opacity(0.4))
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 12)
            .background(Color(UIColor.systemBackground))
            .cornerRadius(8)
            .shadow(color: Color.black.opacity(0.1), radius: 6, x: 0, y: 2)
        }
        .buttonStyle(PlainButtonStyle())
    }
}


#Preview {
    CitizenshipSection()
}
