//
//  ProgramsScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ProgramsScreen: View {
    @StateObject var viewModel : ProgramsViewModel
    
    var body: some View {
        VStack {
            TopAppBar(title: "Programs")
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Text("Visa Categories").customFont(18, weight: .medium).padding(.horizontal,16).padding(.top, 16)
                    
                    ForEach(viewModel.state.visaCategories, id :  \.title) { category in
                        VisaCategoryCard(category: category)
                    }
                }
                .padding(.horizontal, 16)
            }
        }
        .task{
            viewModel.observe()
        }
    }
}



#Preview {
    ScrollView {
        VStack(alignment: .leading, spacing: 16) {
            Text("Visa Categories").customFont(18, weight: .medium).padding(.horizontal,16).padding(.top, 16)
            
            ForEach(VisaCategory.companion.visaCategories, id :  \.title) { category in
                VisaCategoryCard(category: category)
            }
        }
        .padding(.horizontal, 16)
    }
}
