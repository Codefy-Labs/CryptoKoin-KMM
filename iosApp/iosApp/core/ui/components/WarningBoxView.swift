//
//  WarningBoxView.swift
//  iosApp
//
//  Created by Shubham Tomar on 03/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct WarningBoxView: View {
    let title : String
    let message : String
    let image : String
    let retry : () -> Void
    
    init(title: String, message: String, image: String = "warning-vector",   retry: @escaping () -> Void = {} ) {
        self.title = title
        self.message = message
        self.image = image
        self.retry = retry
    }
      
    var body: some View {
        VStack{
            Spacer()
            Image(image)
                .resizable()
                .scaledToFit()
                .frame(width: 200, height: 200)
                .padding()
            
            Text(LocalizedStringKey(title))
                .customFont(size: 20, weight : .medium)
            
            Text(LocalizedStringKey(message))
                .customFont(size: 16, weight : .medium)
                .multilineTextAlignment(.center)
                .padding(.top, 1)
                .padding(.horizontal, 16)
                .foregroundColor(Colors.gray400)
            
            Spacer()
            
        }
    }
}

#Preview {
    WarningBoxView(title: "Oops!!", message: "Something went wrong, please try again.", retry: {} )
}
