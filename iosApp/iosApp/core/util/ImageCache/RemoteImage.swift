//
//  RemoteImage.swift
//  iosApp
//
//  Created by Shubham Tomar on 30/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RemoteImage: View {
    @ObservedObject var imageLoader: ImageLoader

    init(url: String) {
        imageLoader = ImageLoader(url: url)
    }

    var body: some View {
        if let image = imageLoader.image {
            Image(uiImage: image)
                .resizable()
        } else {
            ProgressView()
        }
    }
}

 
