//
//  LoadingView.swift
//  iosApp
//
//  Created by Shubham Tomar on 02/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import Lottie

struct LoadingView: View {
    @State private var rotationAngle: Double = 0

    var body: some View {
        ZStack{
            Color.gray.opacity(0.2).edgesIgnoringSafeArea(.all)
            
            VStack{
                Spacer()
                ZStack {
                    LottieView(animation: .named("loading"))
                        .playing(loopMode: .repeat(.infinity))
                }
                .frame(width: 250, height: 250)
             
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
