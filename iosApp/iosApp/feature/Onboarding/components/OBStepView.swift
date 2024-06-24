//
//  OBStepView.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


struct OnboardingStepView: View {
    var step: SurveyContainer
    var submit : (String) -> Void
    
    @State private var selectedOption: String = ""
    
    var body: some View {
        VStack(spacing : 0) {
            
            
            Spacer()
            
            Image(step.imageName)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(height: 200)
            
            
            Spacer()
            
            Text(step.description)
                .customFont(24, weight: .medium)
                .multilineTextAlignment(.center)
                .frame(maxWidth: .infinity)
                .padding()
                .padding(.horizontal,20)
            
            Spacer()
            
            SelectView(selectedOption: $selectedOption, options: step.options, placeholder: "Select")
                .padding()
                .padding(.horizontal,20)
            
            
            
            Button(action: {
                withAnimation{
                    self.submit(selectedOption)
                }
            }) {
                Text("Continue")
                    .customFont(15, weight: .semiBold)
                    .foregroundColor(.white)
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(selectedOption.isEmpty ? Color.gray : Color.red)
                    .cornerRadius(10)
                    .padding()
                    .padding(.bottom, 40)
                    .padding(.horizontal,20)
            }.disabled( selectedOption.isEmpty )
            
            
        }
    }
}


struct SelectView: View {
    @Binding var selectedOption: String
    var options: [String]
    var placeholder: String
    
    var body: some View {
        Menu {
            ForEach(options, id: \.self) { option in
                Button(action: {
                    selectedOption = option
                }) {
                    Text(option)
                }
            }
        } label: {
            HStack {
                Text(selectedOption.isEmpty ? placeholder : selectedOption)
                    .foregroundColor(selectedOption.isEmpty ? .gray : .black)
                Spacer()
                Image(systemName: "chevron.down")
                    .foregroundColor(.gray)
            }
            .padding()
            .frame(height: 44)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(Color.gray.opacity(0.5), lineWidth: 1)
            )
        }
    }
}




#Preview {
    OnboardingStepView(step: SurveyContainer(imageName: "education", title: "Target Province", description: "Are you planning to immigrate to Canada, or are you already here?", options: ["Option1", "Option2"],selectedOption: "", onboardingStep: .education), submit: { item in })
}
