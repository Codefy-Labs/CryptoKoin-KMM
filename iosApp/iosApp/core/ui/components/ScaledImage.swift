//
//  ScaledImage.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct ScaledImage: View {
    let name: String
    let size: CGSize
    
    var body: Image {
        let uiImage = resizedImage(named: self.name, for: self.size) ?? UIImage()
        
        return Image(uiImage: uiImage.withRenderingMode(.alwaysTemplate))
    }
    
    func resizedImage(named: String, for size: CGSize) -> UIImage? {
        guard let image = UIImage(named: named) else {
            return nil
        }

        let renderer = UIGraphicsImageRenderer(size: size)
        return renderer.image { (context) in
            image.draw(in: CGRect(origin: .zero, size: size))
        }
    }
}
