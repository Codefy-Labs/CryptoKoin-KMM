//
//  CardView.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct CardView<Content: View>: View {
    let content: Content

    init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }

    var body: some View {
        content
            .background(Color(uiColor: UIColor.label.withAlphaComponent(0.08)))
            .cornerRadius(8)
            .shadow(radius: 4)
    }
}
