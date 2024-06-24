//
//  Home.swift
//  CryptoApp
//
//  Created by mk.pwnz on 26.05.2021.
//

import SwiftUI
import shared

struct HomeScreen: View {
    @StateObject var viewModel : HomeViewModel
    
    var body: some View {
        VStack(spacing : 0){
            TopAppBar(title: "Feeds")
            
            if viewModel.state.isLoading {
                LoadingView()
            } else {
                List{
                    trendingSection.listRowSeparator(.hidden)
                        .padding(.bottom,16)
                        .listRowInsets(EdgeInsets())
                      
                    FiltersRow(selectedSegment: Binding(get: {
                        viewModel.state.selectedFilter
                    }, set: { item in viewModel.selectFilter(item)}), segments: viewModel.state.filters)
                    .padding(.bottom,8)
                    .listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets())
                    .listRowSpacing(0)
                      
                    ForEach(viewModel.state.feeds, id:  \.key , content: { item in
                        FeedCardView(imageUrl: item.imageRes, title: item.title, timeAgo: item.daysToGo, views: item.views, comments: item.shares)
                            .padding(.bottom,16)
                            .padding(.horizontal,8)
                            .listRowSeparator(.hidden) .listRowInsets(EdgeInsets())
                        
                    })
                }.listStyle(.plain)
                    .listRowBackground(Color.clear)
            }
      
        }
        
        .task{
            viewModel.observe()
        }
    }
    
}

extension HomeScreen {
    private var trendingSection: some View {
        VStack(alignment: .leading) {
            Text("Trending").customFont(18, weight: .medium).padding(.horizontal,16).padding(.top, 16)
            
            ScrollView(.horizontal, showsIndicators: false) {
                HStack {
                    Spacer().frame(width: 8)
                    ForEach(viewModel.state.trendingFeeds, id: \.key) { item in
                        TrendingCardView(imageUrl: item.imageRes, title: item.title, timeAgo: item.daysToGo, views: item.views, comments: item.views)
                    }
                    Spacer().frame(width: 8)
                }
            }
        }
    }
}
