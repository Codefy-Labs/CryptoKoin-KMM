//
//  ForgetPasswordScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 23/01/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ForgetPasswordScreen: View {
    
    @StateObject var viewModel: ForgetPasswordViewModel
    
    private enum Focus {
        case  email
    }
    
    @FocusState
    private var focus: Focus?
    
    var body: some View {
        
        VStack(alignment : .leading){
            
            BackButton(onClick: viewModel.coordinator.navigateUp)
                .padding(.horizontal)
            
            Spacer().frame(height: 40)
            
            Text("Reset Your Password")
                .customFont(  24, weight: .medium)
                .padding(.horizontal)
                .padding(.vertical,8)
            
            Text("Enter your registered email to receive a confirmation code for resetting your password")
                .customFont(  14, weight: .medium)
                .multilineTextAlignment(.leading)
                .padding(.horizontal)
                .foregroundColor(.black.opacity(0.7))
            
            Spacer().frame(height: 40)
            
            HStack{
                TextField("Email", text: Binding(get: { viewModel.state.email }, set: viewModel.onChangeEmail(_:)))
                    .customFont(16, weight: .medium)
                    .textContentType(.emailAddress)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .disabled(viewModel.state.isLoading || viewModel.state.isOtpSent)
                    .focused($focus, equals: .email)
              
                if viewModel.state.isOtpSent{
                    Image(systemName: "pencil.circle.fill")
                        .resizable()
                        .frame(width: 30, height: 30)
                        .onTapGesture {
                            viewModel.editEmail()
                            focus = .email
                        }
                }
            }.padding(.horizontal)
            
            if viewModel.state.isOtpSent {
                TextField("Confirmation Code", text: Binding(get: { viewModel.state.otp }, set: viewModel.onChangeOtp(_:)))
                    .textContentType(.oneTimeCode)
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                    .disabled( viewModel.state.isLoading)
                
                SecureField("New Password", text: Binding(get: {viewModel.state.newPassword}, set: viewModel.onChangePassword(_:)))
                    .textContentType(.newPassword)
                    .customFont(16, weight: .medium)
                    .padding()
                    .background(Color(UIColor.systemGray6))
                    .cornerRadius(8)
                    .padding(.horizontal)
                    .disabled( viewModel.state.isLoading)

            }
            
            Text("*password at least 8 character with upper, lower digit & special character")
                .customFont(12, weight: .semiBold)
                .multilineTextAlignment(.leading)
                .foregroundColor(.gray.opacity(0.8))
                .padding(.horizontal)
            
            
            Spacer().frame(height: 24)
            
            Button(action: viewModel.handleSubmit) {
                HStack{
                    Text("Submit")
                        .customFont(16, weight: .semiBold)
                    
                    if viewModel.state.isLoading {
                        Spacer().frame(width: 12)
                        ProgressView()
                            .tint(.white)
                            .frame(width: 20, height: 20)
                    }
                }.foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.red)
                    .cornerRadius(8)
                
            }
            .disabled(viewModel.state.isLoading)
            .padding(.horizontal)
            
            Spacer()
            
        }
        .onAppear{
            if !viewModel.state.isOtpSent {
                focus = .email
            }
        }
        .navigationBarBackButtonHidden()
        .task {
            viewModel.observeState()
        }
    }
    
}

