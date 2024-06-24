//
//  DashboardScreen.swift
//  iosApp
//
//  Created by Shubham Tomar on 21/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DashboardScreen: View {
    @StateObject  var viewModel : DashboardViewModel
    
    private let viewFactory = Factories.current.viewFactory
    
    var body: some View {
        VStack{
            TabView(selection: $viewModel.selectedTab,
                    content:  {
                viewFactory.build(screen: .homeScreen)
                    .tabItem { TabContent(title: "Feeds", image: "nav-feeds", isActive: viewModel.selectedTab == 1) }.tag(1)
                
                viewFactory.build(screen: .processScreen)
                    .tabItem { TabContent(title: "Process", image: "nav-process", isActive: viewModel.selectedTab == 2)  }.tag(2)
                
                viewFactory.build(screen: .crsScreen)
                    .tabItem {  TabContent(title: "CRS", image: "nav-crs", isActive: viewModel.selectedTab == 3) }.tag(3)
                
                viewFactory.build(screen: .programsScreen)
                    .tabItem  { TabContent(title: "Programs", image: "nav-programs", isActive: viewModel.selectedTab == 4) }.tag(4)
                    
                viewFactory.build(screen: .profileScreen)
                    .tabItem {  TabContent(title: "Profile", image: "nav-profile", isActive: viewModel.selectedTab == 5)  }.tag(5)
            })
        }.navigationBarBackButtonHidden()
    }
}

private struct TabContent : View {
    
    let title : String
    let image : String
    let isActive : Bool
    
    var body: some View{
        VStack{
            
            ScaledImage(name: image, size: CGSize(width: 24, height: 24))
                .tint(isActive ? Colors.red : Color(uiColor: UIColor.label))
            
            Text(NSLocalizedString(title, comment: ""))
                .customFont(  12, weight: isActive ? .bold : .medium)
                .foregroundColor(isActive ? Colors.red : Color(uiColor: UIColor.label))
        }
    }
}


//#Preview {
//    DashboardScreen(v)
//}
