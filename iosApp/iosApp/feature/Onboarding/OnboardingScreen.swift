//
//  OnboardingScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 24/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct OnboardingScreen: View {
    
    @StateObject var viewModel : OnboardingViewModel
    
    var body: some View {
        VStack{
            
            HStack {
                
                Button(action: {
                    viewModel.coordinator.navigateUp()
                }) {
                    Image(systemName: "xmark")
                        .foregroundColor(.gray)
                        .font(.system(size: 24))
                }
                
                Spacer()
                
                if !viewModel.steps.isEmpty && viewModel.currentStep < viewModel.steps.count {
                    Text( viewModel.steps[viewModel.currentStep].title)
                        .customFont(22, weight: .bold)
                        .padding()
                }
                
                
                Spacer()
                
                Button(action: {
                    withAnimation{
                        viewModel.skip()
                    }
                }) {
                    Text("Skip")
                        .foregroundColor(.blue)
                        .font(.system(size: 16))
                }
            }.padding()
                .zIndex(1.0)
            
            
            
            
            TabView(selection: $viewModel.currentStep) {
                ForEach(viewModel.steps.indices, id: \.self) { index in
                    OnboardingStepView(
                        step: viewModel.steps[index],
                        submit: { selectedOption in
                            viewModel.submit(selectedOption)
                        }
                    ).tag(index)
                }
            }
            .onAppear {
                UIScrollView.appearance().isScrollEnabled = false
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
            
            
            HStack {
                ForEach(viewModel.steps.indices, id: \.self) { index in
                    Circle()
                        .fill(index == viewModel.currentStep ? Color.accentColor : Color.gray)
                        .frame(width: 10, height: 10)
                }
            }
            .padding(.bottom, 20)
            
        }.navigationBarBackButtonHidden()
            .task {
                viewModel.observe()
            }
    }
}


