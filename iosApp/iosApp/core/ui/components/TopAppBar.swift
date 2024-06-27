//
//  TopAppBar.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TopAppBar: View {
    var title: String
    
    var body: some View {
        HStack {
            Text(title)
                .font(.title)
                .fontWeight(.bold)
                .foregroundColor(.white)
            
            Spacer()
            
            ZStack {
                Image(systemName: "bell.fill")
                    .foregroundColor(.white)
                    .font(.system(size: 24))
                
                Circle()
                    .fill(Color.yellow)
                    .frame(width: 8, height: 8)
                    .offset(x: 10, y: -10)
            }
        }
        .padding()
        .background(Color.red)
    }
}

struct TopAppBarWithBackButton: View {
    var title: String
    let onBackPressed: () -> Void
    let actions: () -> AnyView  
    
    var body: some View {
        HStack {
            BackButton(onClick: onBackPressed)
            
            Text(title)
                .font(.title)
                .fontWeight(.bold)
                .frame(maxWidth: .infinity, alignment: .center)
            
            actions()
        }
        .foregroundColor(.white)
        .padding()
        .background(Color.red)
    }
}

 


#Preview {
    VStack{
        TopAppBar(title: "Feeds")
        Spacer()
    }
}
