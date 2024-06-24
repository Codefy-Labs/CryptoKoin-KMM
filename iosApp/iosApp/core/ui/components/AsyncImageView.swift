//
//  AsyncImageView.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//



import SwiftUI

struct AsyncImageView: View {
    var url: URL
    var placeholder: String
    var width : CGFloat = 100
    var height : CGFloat = 100
    
    var body: some View {
        AsyncImage(url: url) { phase in
            switch phase {
            case .empty:
                ShimmerView()
                    .frame(width : width, height: height)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .clipped()
            case .success(let image):
                image
                    .resizable()
                    .scaledToFill()
                    .frame(width : width, height: height)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .clipped()
            case .failure:
                Image(placeholder)
                    .resizable()
                    .scaledToFit()
                    .frame(width : width, height: height)
                    .background(Colors.gray100)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .clipped()
            @unknown default:
                Image(placeholder)
                    .resizable()
                    .scaledToFit()
                    .frame(width : width, height: height)
                    .background(Colors.gray100)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .clipped()
            }
        }
    }
}

struct ShimmerView: View {
    @State private var shimmer = false
    
    var body: some View {
        ZStack {
            Color.gray.opacity(0.3)
            Color.white.opacity(0.6)
                .mask(
                    Rectangle()
                        .fill(LinearGradient(gradient: Gradient(colors: [.clear, .white.opacity(0.6), .clear]), startPoint: .leading, endPoint: .trailing))
                        .rotationEffect(.degrees(30))
                        .offset(x: shimmer ? 600 : -600)
                )
        }
        .onAppear {
            withAnimation(Animation.linear(duration: 1.5).repeatForever(autoreverses: false)) {
                shimmer.toggle()
            }
        }
    }
}

struct AsyncImageView_Previews: PreviewProvider {
    static var previews: some View {
        AsyncImageView(url: URL(string: "https://example.com/image.jpg")!, 
                       placeholder : "news-placeholder"
    )
            
    }
}

 
