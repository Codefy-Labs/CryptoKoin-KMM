//
//  LoginScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
 

struct LoginScreen: View {
    @StateObject var viewModel : LoginViewModel

    var body: some View {
        VStack(spacing: 20) {
            HStack{
                BackButton(onClick: viewModel.coordinator.navigateUp)
                Spacer()
            }
            
            Spacer()
            
            Image("logo-transparent")
                .resizable()
                .scaledToFit()
                .padding()
            Spacer()

            // Username TextField
            TextField("Email Id", text: Binding(get: {
                viewModel.state.emailId
            }, set: { value in
                viewModel.onChangeEmail(value)
            })).customFont(16, weight: .medium)
                .disabled(viewModel.state.isLoading)
                .padding()
                .background(Color(UIColor.systemGray6))
                .cornerRadius(8)
                .padding(.horizontal)

            // Password SecureField
            SecureField("Password", text: Binding(get: {
                viewModel.state.password
            }, set: { value in
                viewModel.onChangePassword(value)
            }))
                .customFont(16, weight: .medium)
                .disabled(viewModel.state.isLoading)
                .padding()
                .background(Color(UIColor.systemGray6))
                .cornerRadius(8)
                .padding(.horizontal)

            // Remember Me and Forgot Password
            HStack {
                Spacer()
                Button(action: viewModel.coordinator.showForgetPassword) {
                    Text("Forgot Password")
                        .foregroundColor(.blue)
                        .customFont(14, weight: .medium)
                }
            }
            .padding(.horizontal)

            // Continue Button
            Button(action: viewModel.signIn) {
                HStack{
                    Text("Continue")
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
                Task{  await viewModel.signInWithGoogle()   }
            }) {
                HStack {
                    Image( "ic-google")
                        .resizable()
                        .frame(width: 24, height: 24)
                    Spacer().frame(width: 12)
                    Text("Login with Google")
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
            .disabled(viewModel.state.isGoogleSigning)
            .padding(.horizontal)

            // Sign Up Link
            HStack {
                Text("Don’t have an account?")
                Button(action: {
                    viewModel.coordinator.showSignup()
                }) {
                    Text("Sign Up")
                        .foregroundColor(.blue)
                }
            }.customFont(16, weight: .medium)
            .padding(.top, 20)
            
            Spacer()
        }
        .padding()
        .navigationBarBackButtonHidden()
        .task {
            viewModel.observe()
        }
    }
}

struct CheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        Button(action: {
            configuration.isOn.toggle()
        }) {
            HStack {
                Image(systemName: configuration.isOn ? "checkmark.square" : "square")
                configuration.label
            }
        }
    }
}

 
