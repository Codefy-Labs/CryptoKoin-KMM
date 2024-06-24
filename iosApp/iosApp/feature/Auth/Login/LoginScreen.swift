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
    @State private var username: String = ""
    @State private var password: String = ""

    var body: some View {
        VStack(spacing: 20) {
            
            Spacer()
            Image("logo-transparent")
                .resizable()
                .scaledToFit()
                .padding()
            Spacer()

            // Username TextField
            TextField("User name", text: $username)
                .customFont(16, weight: .medium)
                .padding()
                .background(Color(UIColor.systemGray6))
                .cornerRadius(8)
                .padding(.horizontal)

            // Password SecureField
            SecureField("Password", text: $password)
                .customFont(16, weight: .medium)
                .padding()
                .background(Color(UIColor.systemGray6))
                .cornerRadius(8)
                .padding(.horizontal)

            // Remember Me and Forgot Password
            HStack {
                Spacer()
                Button(action: {
                    // Forgot password action
                }) {
                    Text("Forgot Password")
                        .foregroundColor(.blue)
                        .customFont(14, weight: .medium)
                }
            }
            .padding(.horizontal)

            // Continue Button
            Button(action: {
                // Continue button action
            }) {
                Text("Continue")
                    .customFont(16, weight: .semiBold)
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.red)
                    .cornerRadius(8)
                   
            }
            .padding(.horizontal)

            // Login with Google Button
            Button(action: {
                // Login with Google action
            }) {
                HStack {
                    Image( "ic-google")
                        .resizable()
                        .frame(width: 24, height: 24)
                    Spacer().frame(width: 12)
                    Text("Login with Google")
                        .customFont(16, weight: .medium)
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

            // Sign Up Link
            HStack {
                Text("Don’t have an account?")
                Button(action: {
                    // Sign up action
                }) {
                    Text("Sign Up")
                        .foregroundColor(.blue)
                }
            }.customFont(16, weight: .medium)
            .padding(.top, 20)
        }
        .padding()
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

 
