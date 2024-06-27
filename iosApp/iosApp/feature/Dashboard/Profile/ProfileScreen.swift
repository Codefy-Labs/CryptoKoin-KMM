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
            
            ScrollView(.vertical, showsIndicators: false){
                VStack{
                    loginPlaceholder.padding()
                    
                    profileBody.padding()
                      
                    settings.padding(.horizontal)
                }
            }
            
            
        }
        .task{
            viewModel.observe()
        }
    }
}

extension ProfileScreen {
    var profileBody: some View {
        Group {
            if let session = viewModel.state.session {
                VStack(spacing: 16) {
                    ProfilePicture(pictureUrl: session.profilePicture, placeholder: session.getNameInitials())
                    
                    VStack {
                        Text(session.name ?? "")
                            .customFont(20, weight: .semiBold)
                        
                        Text(session.email ?? "")
                            .customFont(16)
                            .foregroundColor(.secondary)
                        
                        Button(action: {
                            // Edit profile action
                        }) {
                            Text("Edit profile")
                                .customFont(12, weight: .medium)
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
                }
            } else {
                EmptyView()
            }
        }
    }
    
    var settings : some View {
        Group{
            VStack(spacing : 0){
                if viewModel.state.session != nil {
                    Divider().background(Color.primary.opacity(0.6))
                    OptionItem(title: "Delete My Account", iconName: "ic-delete")
                }
                Divider().background(Color.primary.opacity(0.6))
                OptionItem(title: "Feedback", iconName: "ic-feedback")
                Divider().background(Color.primary.opacity(0.6))
                OptionItem(title: "Tasks", iconName: "ic-tasks")
                Divider().background(Color.primary.opacity(0.6))
                OptionItem(title: "Support", iconName: "ic-headphone")
                Divider().background(Color.primary.opacity(0.6))
                
                if viewModel.state.session != nil {
                    OptionItem(title: "Logout", iconName: "ic-logout").onTapGesture {
                        viewModel.logout()
                    }
                    Divider().background(Color.primary.opacity(0.6))
                }
            }
        }
    }
    
    var loginPlaceholder : some View {
        Group{
            if viewModel.state.session == nil {
                Button("Login", action: {
                    viewModel.coordinator.showLogin()
                })
            }
        }
    }
}

struct ProfilePicture: View {
    let pictureUrl : String?
    let placeholder : String
    
    var body: some View {
        ZStack {
            Circle()
                .fill(Color.primary)
                .frame(width: 80, height: 80)
            Text(placeholder)
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
    }
}


