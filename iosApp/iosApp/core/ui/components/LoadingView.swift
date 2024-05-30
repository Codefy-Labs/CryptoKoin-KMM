//
//  LoadingView.swift
//  iosApp
//
//  Created by Shubham Tomar on 02/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct LoadingView: View {
    @State private var rotationAngle: Double = 0

    var body: some View {
        ZStack{
            Color.gray.opacity(0.5).edgesIgnoringSafeArea(.all)
            
            VStack{
                Spacer()
                ZStack {
                    Colors.blue.opacity(0.9)
                        .edgesIgnoringSafeArea(.all)

                    Image("logo")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 50, height: 50)
                        .foregroundColor(.blue)
                        .rotationEffect(.degrees(rotationAngle))
                        .onAppear() {
                            withAnimation(Animation.linear(duration: 1).repeatForever(autoreverses: false)) {
                                rotationAngle = 360
                            }
                        }
                }
                .frame(width: 120, height: 120)
                .clipShape(Circle())
                Spacer()
            }
            
        }

    }
}

extension View {
    func loadingView(_ isLoading: Bool) -> some View { 
        ZStack {
            self
            if isLoading {
                LoadingView()
            }
        }
    }
}


struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        VStack{
            LoadingView()
                 
                 
        }
    }
}
