//
//  NewsDetailView.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct NewsDetailView : View {
    
    
    let url = "https://www.cicnews.com/2024/06/breaking-quebec-imposes-cap-on-family-sponsorship-applications-0645040.html#gs.bf8lu9"
    
    let thumbnail = "https://www.cicnews.com/wp-content/uploads/2024/06/Quebec-sets-new-rules-and-processing-cap-for-family-sponsorship-applications-1024x683.jpg"
    
    var body : some View {
        VStack{
            if url.isEmpty {
                AsyncImageView(url: URL(string: url)!, placeholder: "news-placeholder")
                    .frame(height: 120)
            }else {
                
            }
        }
    }
}

#Preview{
    NewsDetailView()
}
