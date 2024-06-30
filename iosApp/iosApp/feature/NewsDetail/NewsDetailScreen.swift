//
//  NewsDetailScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 27/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct NewsDetailScreen: View {
    
    @StateObject var viewModel : NewsDetailViewModel
    @State private var isScrolling = false
    @State private var fabExpanded = true
    @State private var lastContentOffset: CGFloat = .zero
    
    
    @State var selectedTab = 0
    
     
    let url = "https://www.cicnews.com/2024/06/breaking-quebec-imposes-cap-on-family-sponsorship-applications-0645040.html#gs.bf8lu9"
    
    let thumbnail = "https://www.cicnews.com/wp-content/uploads/2024/06/Quebec-sets-new-rules-and-processing-cap-for-family-sponsorship-applications-1024x683.jpg"
    @State var webViewHeight: CGFloat = 0
    
    var body: some View {
        VStack(alignment : .leading, spacing : 0){
             
            NewsDetailTabs(selectedTab : $selectedTab, navigateUp: viewModel.coordinator.navigateUp)
            
            VStack(spacing : 0){
                TabView(selection: $selectedTab,
                        content:  {
                    VStack{
                        GeometryReader { geometry in
                            Color.clear
                                .preference(key: ScrollOffsetPreferenceKey.self, value: geometry.frame(in: .global).minY)
                            
                            ScrollView {
                                
                                if url.isEmpty {
                                  RemoteImage(url: url)
                                        .frame(height: 120)
                                }else {
                                    if let url = URL(string:url) {
                                        WebView(urlRequest: URLRequest(url:url), contentHeight: self.$webViewHeight)
                                            .frame(width: geometry.size.width, height: max(geometry.size.height, self.webViewHeight))
                                            .edgesIgnoringSafeArea(.bottom)
                                    }
                                }
                                
                            } .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                                handleScroll(value: value)
                            }
                        }
                    }.tag(0)
                    
                    DiscussionsView().tag(1)
                }).tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
            }
            
            
        }.navigationBarBackButtonHidden()
    }
    
    private func handleScroll(value: CGFloat) {
        withAnimation { fabExpanded = value > 60 }
        lastContentOffset = value
    }
}

