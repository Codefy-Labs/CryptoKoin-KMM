//
//  SplashScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 20/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SplashScreen: View {
    var body: some View {
        VStack{
            Image("logo")
                .resizable()
                .scaledToFit()
                .frame(width: 150, height: 150)
        } 
    }
}

#Preview {
    SplashScreen()
}
