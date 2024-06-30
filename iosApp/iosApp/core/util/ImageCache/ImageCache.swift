//
//  ImageCache.swift
//  iosApp
//
//  Created by Shubham Tomar on 30/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit


class ImageCache {
    static let shared = ImageCache()

    private let cache = NSCache<NSString, UIImage>()

    private init() {}

    func set(_ image: UIImage, forKey key: String) {
        cache.setObject(image, forKey: key as NSString)
    }

    func get(forKey key: String) -> UIImage? {
        return cache.object(forKey: key as NSString)
    }
}
