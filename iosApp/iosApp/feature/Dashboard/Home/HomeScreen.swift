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
                    
                   newsListView
                  
                }.listStyle(.plain)
                    .listRowBackground(Color.clear)
                    .refreshable {
                        viewModel.refresh()
                    }
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
                    ForEach(viewModel.state.trendingFeeds, id: \.id) { item in
                        TrendingCardView(imageUrl: item.imageRes, title: item.title, timeAgo: item.daysToGo, views: item.views, comments: item.views)
                            .onTapGesture {
                                viewModel.coordinator.showNewsDetail(newsId: item.id)
                            }
                    }
                    Spacer().frame(width: 8)
                }
            }
        }
    }
    
    
    private var newsListView : some View {
        Group{
            
            if let newsArray = viewModel.state.news.data as? [News] {
                ForEach(newsArray, id:  \.id, content: { item in
                    FeedCardView(imageUrl: item.thumbnailUrl, title: item.title, timeAgo: item.publishedAt, views: String(item.views), comments: String(item.shares))
                        .onTapGesture {
                            viewModel.coordinator.showNewsDetail(newsId: String(item.id))
                        }
                        .padding(.bottom,16)
                        .padding(.horizontal,8)
                        .listRowSeparator(.hidden) .listRowInsets(EdgeInsets())
                        .onAppear(perform: {
                            if newsArray.last == item {
                                viewModel.loadMoreNews()
                            }
                        })
                    
                })
            }
            
            
            if viewModel.state.news.isRefreshing {
                LoadingView(width: 100, height: 100, background: .clear)
                    .listRowSeparator(.hidden) .listRowInsets(EdgeInsets())
            }
        }
    }
}
