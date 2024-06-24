//
//  ProfileScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileScreen: View {
    @StateObject var viewModel : ProfileViewModel
  
    var body: some View {
        VStack {
            TopAppBar(title: "Profile")
            ProfileBody()
        }
        .task{
            viewModel.observe()
        }
    }
}

struct ProfileBody: View {
    var body: some View {
        ScrollView(.vertical, showsIndicators: false, content: {
            VStack(spacing: 16) {
                ProfilePicture()
                
                VStack{
                    Text("Ankit Angra")
                        .customFont( 20, weight: .semiBold)
                    
                    Text("ankitangra@codefylabs.com")
                        .customFont( 16)
                        .foregroundColor(.secondary)
                    
                    Button(action: {
                        // Edit profile action
                    }) {
                        Text("Edit profile")
                            .customFont( 12, weight: .medium)
                            .padding(.horizontal, 20)
                            .padding(.vertical, 2)
                            .frame(height: 28)
                            .background(Color.primary)
                            .foregroundColor(.white)
                            .cornerRadius(18)
                    }
                    .buttonStyle(PlainButtonStyle())
                    .padding()
                }
                
               
                VStack(spacing: 0) {
                    Divider().background(Color.primary.opacity(0.6))
                    OptionItem(title: "Delete My Account", iconName: "ic-delete")
                    Divider().background(Color.primary.opacity(0.6))
                    OptionItem(title: "Feedback", iconName: "ic-feedback")
                    Divider().background(Color.primary.opacity(0.6))
                    OptionItem(title: "Tasks", iconName: "ic-tasks")
                    Divider().background(Color.primary.opacity(0.6))
                    OptionItem(title: "Support", iconName: "ic-headphone")
                    Divider().background(Color.primary.opacity(0.6))
                    OptionItem(title: "Logout", iconName: "ic-logout")
                    Divider().background(Color.primary.opacity(0.6))
                }
            }
            .padding(16)
        })
    }
}

struct ProfilePicture: View {
    var body: some View {
        ZStack {
            Circle()
                .fill(Color.primary)
                .frame(width: 80, height: 80)
            Text("AG")
                .font(.system(size: 24))
                .foregroundColor(.white)
        }
    }
}

struct OptionItem: View {
    let title: String
    let iconName: String
    
    var body: some View {
        HStack {
            Image( iconName)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundColor(.primary.opacity(0.6))
            
            Text(title)
                .customFont( 16, weight: .medium)
                .foregroundColor(.primary.opacity(0.6))
            Spacer()
            
            Image(systemName: "chevron.right")
                .foregroundColor(.primary.opacity(0.6))
            
        }
        .padding(.vertical, 16)
        .contentShape(Rectangle())
        .onTapGesture {
            
        }
    }
}

#Preview {
    ProfileBody()
}
