//
//  ProcessScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProcessScreen: View {
    @StateObject var viewModel : ProcessViewModel
    
    @State private var selectedTab = 0
    let tabs = ["Express Entry", "Citizenship"]
    
    var body: some View {
        VStack {
            TopAppBar(title: "Process")
            VStack {
                HStack {
                    Spacer()
                    ForEach(0..<tabs.count, id: \.self) { index in
                        TabButton(selectedTab: $selectedTab, index: index, title: tabs[index])
                            .padding(.top, 8)
                    }
                    Spacer()
                }
                .padding(.horizontal,16)
                .background(Color(UIColor.systemBackground))
                 
                
                TabView(selection: $selectedTab) {
                    ExpressEntrySection().tag(0)
                    CitizenshipSection().tag(1)
                }
                .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
            }
        }
        .task{
            viewModel.observe()
        }
    }
}

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



//#Preview {
//    ProcessScreen()
//}
