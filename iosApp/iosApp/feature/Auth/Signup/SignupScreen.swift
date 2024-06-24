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
    
    @State private var username: String = ""
    @State private var password: String = ""

    var body: some View {
        ScrollView(showsIndicators : false){
            VStack(spacing : 20) {
                // Logo
                Spacer()
                Image("logo-transparent")
                    .resizable()
                    .scaledToFit()
                    .padding()
                Spacer()

                // Username TextField
                TextField("Full Name", text: $username)
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                 
                TextField("Email", text: $username)
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
                
                Text("*password at least 8 character with upper, lower digit & special character")
                    .customFont(12, weight: .semiBold)
                    .multilineTextAlignment(.leading)
                    .foregroundColor(.gray)

                
                // Password SecureField
                SecureField("Confirm Password", text: $password)
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
 
                // Continue Button
                Button(action: {
                    // Continue button action
                }) {
                    Text("Sign Up")
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
     
            }
        }
        .padding()
    }
}

 
 
