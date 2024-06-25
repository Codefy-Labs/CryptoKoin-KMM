//
//  BackButton.swift
//  iosApp
//
//  Created by Shubham Tomar on 25/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct BackButton: View {
    let onClick : () -> Void
    
    var body: some View {
        Button(action: {
            onClick()
        }, label: {
            Image(systemName: "arrow.left")
        })
    }
}

#Preview {
    BackButton(onClick: {})
}
