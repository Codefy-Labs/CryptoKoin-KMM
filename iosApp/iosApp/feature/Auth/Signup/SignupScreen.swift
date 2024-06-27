//
//  SignupScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
 

struct SignupScreen: View {
    @StateObject var viewModel : SignupViewModel
     
    var body: some View {
        ScrollView(showsIndicators : false){
            VStack(spacing : 20) {
             
                HStack{ 
                    BackButton(onClick: viewModel.coordinator.navigateUp)
                    Spacer()
                }
                
                Image("logo-transparent")
                    .resizable()
                    .scaledToFit()
                    .padding()
                Spacer()

                // Username TextField
                TextField("Full Name", text: Binding(get: { viewModel.state.name }, set: viewModel.onChangeName(_:)))
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                    .disabled( viewModel.state.isLoading)
                 
                TextField("Email", text: Binding(get: {viewModel.state.email }, set: viewModel.onChangeEmail(_:)))
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                    .disabled( viewModel.state.isLoading)

                // Password SecureField
                SecureField("Password", text: Binding(get: {viewModel.state.password}, set: viewModel.onChangePassword(_:)))
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                    .disabled( viewModel.state.isLoading)
                
                Text("*password at least 8 character with upper, lower digit & special character")
                    .customFont(12, weight: .semiBold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(.gray)

                
                // Password SecureField
                SecureField("Confirm Password", text: Binding(get: {viewModel.state.confirmPassword}, set: viewModel.onChangeConfirmPassword(_:)))
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                    .disabled( viewModel.state.isLoading)
 
                // Continue Button
                Button(action: viewModel.signUp) {
                    HStack{
                        Text("Sign Up")
                            .customFont(16, weight: .semiBold)
                            
                        if viewModel.state.isLoading {
                            Spacer().frame(width: 12)
                            ProgressView()
                                .tint(.white)
                                .frame(width: 20, height: 20)
                        }
                    } .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.red)
                        .cornerRadius(8)
                       
                }
                .disabled(viewModel.state.isLoading)
                .padding(.horizontal)

                // Login with Google Button
                Button(action: {
                    Task{
                        await viewModel.signUpWithGoogle()
                    }
                }) {
                    HStack {
                        Image( "ic-google")
                            .resizable()
                            .frame(width: 24, height: 24)
                        Spacer().frame(width: 12)
                        Text("SignUp with Google")
                            .customFont(16, weight: .medium)
                        if viewModel.state.isGoogleSigning {
                            Spacer().frame(width: 12)
                            ProgressView()
                                .frame(width: 24, height: 24)
                        }
                    }
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.white)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(Color.gray, lineWidth: 1)
                    )
                }
                .padding(.horizontal)
     
            }
        }
        .task{
            viewModel.observeState()
        }
        .padding()
        .navigationBarBackButtonHidden()
    }
}

 
 
