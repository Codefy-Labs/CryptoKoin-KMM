//
//  TrendingCardView.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI 

struct TrendingCardView: View {
    var imageUrl: String
    var title: String
    var timeAgo: String
    var views: String
    var comments: String
    
    var body: some View {
        VStack(alignment: .leading) {
            RemoteImage(url:  imageUrl)
                .frame(width: 160, height: 240)
            
            Text(title)
                .customFont(12, weight: .medium)
                .lineLimit(5)
                .truncationMode(.tail)
                .padding(.vertical, 4)
            
            Text("Read more")
                .customFont(12, weight: .semiBold)
                .foregroundColor(.blue)
                .padding(.bottom, 4)
            
            HStack {
                Text(timeAgo)
                    .customFont(9, weight: .medium)
                    .foregroundColor(.gray)
                
                Spacer()
                
                HStack(spacing: 4) {
                    Image(systemName: "eye")
                        .resizable()
                        .frame(width : 14, height: 9)
                        .foregroundColor(.gray)
                    
                    Text("\(views)")
                        .customFont(9, weight: .medium)
                        .foregroundColor(.gray)
                    
                    Spacer().frame(width: 4)
                    
                    Image(systemName: "bubble.right")
                        .resizable()
                        .frame(width : 14, height: 12)
                        .foregroundColor(.gray)
                    
                    Text("\(comments)")
                        .customFont(9, weight: .medium)
                        .foregroundColor(.gray)
                }
            }
            .padding(.bottom, 4)
        }
        .frame(width: 160)
        .padding(.horizontal, 8)
        .cornerRadius(8)
    }
}

struct FeedCardView: View {
    var imageUrl: String
    var title: String
    var timeAgo: String
    var views: String
    var comments: String
    
    var body: some View {
        HStack {
            VStack(alignment : .leading){
                Text(title)
                    .customFont(12, weight: .medium)
                    .lineLimit(3)
                    .truncationMode(.tail)
                    .padding(.vertical, 4)
                  
                HStack {
                    Text(timeAgo)
                        .customFont(9, weight: .medium)
                        .foregroundColor(.gray)
                    
                    Spacer().frame(width: 16)
                    
                    HStack(spacing: 4) {
                        Image(systemName: "eye")
                            .resizable()
                            .frame(width : 14, height: 9)
                            .foregroundColor(.gray)
                        
                        Text("\(views)")
                            .customFont(9, weight: .medium)
                            .foregroundColor(.gray)
                        
                        Spacer().frame(width: 4)
                        
                        Image(systemName: "bubble.right")
                            .resizable()
                            .frame(width : 14, height: 12)
                            .foregroundColor(.gray)
                        
                        Text("\(comments)")
                            .customFont(9, weight: .medium)
                            .foregroundColor(.gray)
                    }
                }
                Spacer()
            }
            
            Spacer()
            
//            AsyncImageView(url: URL(string: imageUrl)!,
//                           placeholder: "news-placeholder",
//                           width: 100, height: 80)
            
            RemoteImage(url: imageUrl)
                          .frame(width: 100, height: 100, alignment: .center)
                          .cornerRadius(4)
        }.padding()
            .background(Color(uiColor: UIColor.label.withAlphaComponent(0.01)))
            .cornerRadius(4)
        .padding(.horizontal, 8)
     
    }
}

struct CardView_Previews: PreviewProvider {
    static var previews: some View {
        TrendingCardView(imageUrl : "https://assets.studies-overseas.com/Great_News_for_Foreign_Nationals_Canada_Extends_Post_Graduation_banner_46203ca347.jpg", title: "Immigration, Refugees and Citizenship Canada (IRCC) has issued its first round of Express Entry invitations ",   timeAgo: "2 days to go", views: "12", comments: "23")
            .previewLayout(.sizeThatFits)
    }
}

  struct FeedCardView_Previews: PreviewProvider {
    static var previews: some View {
        FeedCardView(imageUrl : "https://assets.studies-overseas.com/Great_News_for_Foreign_Nationals_Canada_Extends_Post_Graduation_banner_46203ca347.jpg", title: "Immigration, Refugees and Citizenship Canada (IRCC) has issued its first round of Express Entry invitations ",   timeAgo: "2 days to go", views: "12", comments: "23")
            .previewLayout(.sizeThatFits)
    }
}

  
