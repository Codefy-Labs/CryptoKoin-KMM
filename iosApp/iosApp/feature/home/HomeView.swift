    //
    //  Home.swift
    //  CryptoApp
    //
    //  Created by mk.pwnz on 26.05.2021.
    //

import SwiftUI
import shared

struct HomeScreen: View {
    @StateObject var vm : HomeIOSViewModel
    
    @SwiftUI.State private var showPortfolioSheet: Bool = false // show new sheet
    @SwiftUI.State private var showPortfolioCoinsList: Bool = false     // animate button
    @SwiftUI.State private var showSettingsView: Bool = false
    @SwiftUI.State private var showDetailView: Bool = false
    
    var body: some View {
        ZStack {
                // background
            Color.theme.background
                .ignoresSafeArea()
                .sheet(isPresented: $showPortfolioSheet, onDismiss: {
                    vm.onSearchTextChange("")
                    vm.removeSelectedCoin()
                }) {
                    PortfolioView()
                        .environmentObject(vm)
                }
            
            VStack {
                homeHeader
                
                HomeStatisticView(showPortfolio: $showPortfolioCoinsList)
                
                SearchBarView(searchText: Binding(get: {
                    vm.state.searchText
                }, set: { value in
                    vm.onSearchTextChange(value)
                }))
                
                columnTitles
                
                if !showPortfolioCoinsList {
                    allCoinsList
                        .transition(.move(edge: .leading))
                }
                
                if showPortfolioCoinsList {
                    ZStack(alignment: .top) {
                        if vm.state.portfolioCoins.isEmpty {
                            portfolioEmptyText
                        } else {
                            portfolioCoinsList
                        }
                    }
                    .transition(.move(edge: .trailing))
                }
                
                Spacer(minLength: 0)
            }
            .sheet(isPresented: $showSettingsView) {
//                SettingsView()
            }
        }
        .environmentObject(vm)
        .task{
            vm.observe()
        }
        .background(
//            NavigationLink(
//                destination: DetailLoadingView(coin: $selectedCoin),
//                isActive: $showDetailView,
//                label: { EmptyView() }
//            )
        )
    }
}

extension HomeScreen {
    private var homeHeader: some View {
        HStack {
            CircleButtonView(iconName: showPortfolioCoinsList ? "plus" : "gear")
                .onTapGesture {
                    if showPortfolioCoinsList {
                        showPortfolioSheet.toggle()
                    } else {
                        showSettingsView.toggle()
                    }
                }
                .animation(.none, value: showPortfolioCoinsList)
                .background(CircleButtonAnimationView(animate: $showPortfolioCoinsList))
            
            Spacer()
            
            Text(showPortfolioCoinsList ? "Portfolio" : "Live Prices")
                .font(.headline)
                .fontWeight(.heavy)
                .foregroundColor(.theme.accent)
                .animation(.none)
            
            Spacer()
            
            CircleButtonView(iconName: "chevron.right")
                .rotationEffect(Angle(degrees: showPortfolioCoinsList ? 180 : 0))
                .onTapGesture {
                    withAnimation(.spring()) {
                        showPortfolioCoinsList.toggle()
                    }
                }
        }
        .padding(.horizontal)
        
    }
    
    private var allCoinsList: some View {
        List {
            ForEach(vm.state.allCoins, id: \.id) { coin in
                CoinRowView(coin: coin, showHoldingsColumn: false)
                    .listRowInsets(.init(top: 10, leading: 0, bottom: 10, trailing: 10))
                    .onTapGesture {
                        segue(coin: coin)
                    }
                    .swipeActions(allowsFullSwipe: true) {
                        Button(action: { addCoinOnSwipeAction(coin: coin) }, label: {
                            Image(systemName: "plus")
                        })
                            .tint(.green)
                        
                    }
            }
        }
        .listStyle(PlainListStyle())
        .refreshable {
            vm.reloadData()
        }
    }
    
    private var portfolioCoinsList: some View {
        List {
            ForEach(vm.state.portfolioCoins, id : \.id) { coin in
                CoinRowView(coin: coin, showHoldingsColumn: true)
                    .listRowInsets(.init(top: 10, leading: 0, bottom: 10, trailing: 10))
                    .onTapGesture {
                        segue(coin: coin)
                    }
                    .swipeActions(allowsFullSwipe: true) {
                        Button(action: {
                            vm.updatePortfolio(coin: coin, amount: 0)
                        }, label: {
                            Image(systemName: "trash.fill")
                        })
                        .tint(.red)
                        
                        Button(action: { editCoinOnSwipeAction(coin: coin) }, label: {
                            Image(systemName: "gear")
                        })
                       
                    }
            }
            
        }
        .listStyle(PlainListStyle())
        .refreshable {
            vm.reloadData()
        }
    }
    
    private func addCoinOnSwipeAction(coin: Coin) {
        // looks for the coin in user's portfolio
        // if the coin in portfolio, we use it to get `.currentHoldings`
        let coinFromPortfolio = vm.state.portfolioCoins.first(where: { $0.id == coin.id })
         
        vm.updateSelectedCoin(coin: coinFromPortfolio ?? coin)
        vm.onSearchTextChange( coin.symbol.uppercased())
        vm.onCoinsQuantityTextChange(coinFromPortfolio?.currentHoldings?.stringValue ?? "")
        showPortfolioSheet = true
    }
    
    private func editCoinOnSwipeAction(coin: Coin) {
        showPortfolioSheet = true
        vm.updateSelectedCoin(coin: coin)
        vm.onSearchTextChange( coin.symbol.uppercased())
        vm.onCoinsQuantityTextChange( coin.currentHoldings?.stringValue ?? "")
    }
    
    private func removeCoinsFromPortfolio(at offsets: IndexSet) {
        let coinsToUpdate = offsets.map { vm.state.portfolioCoins[$0] }
        for coin in coinsToUpdate {
            vm.updatePortfolio(coin: coin, amount: 0)
        }
    }
    
    private var portfolioEmptyText: some View {
        Text("No coins in portfolio. \n Maybe should add something? 🤔")
            .font(.callout)
            .foregroundColor(.theme.accent)
            .fontWeight(.medium)
            .multilineTextAlignment(.center)
            .padding(50)
    }
    
    private func segue(coin: Coin) {
//        selectedCoin = coin
//        showDetailView.toggle()
    }
    
    private var columnTitles: some View {
        HStack {
            columnTitleCoin
            
            Spacer()
            
            if showPortfolioCoinsList {
                columnTitleHoldings
            }
            
            columnTitlePrice
             
        }
        .font(.caption)
        .foregroundColor(.theme.secondaryText)
        .padding(.horizontal)
    }
    
    private var columnTitleCoin: some View {
        HStack(spacing: 4) {
            Text("Coin")
            Image(systemName: "chevron.down")
                .opacity(
                    vm.state.sortOption == .rank ||
                    vm.state.sortOption == SortOption.rankreversed ? 1 : 0
                )
                .rotationEffect(Angle(degrees: vm.state.sortOption == .rank ? 0 : 180))
        }
        .onTapGesture {
            withAnimation(.default) { 
                vm.updateSortOption(vm.state.sortOption == .rank ? SortOption.rankreversed : .rank)
            }
        }
    }
    
    private var columnTitleHoldings: some View {
        HStack(spacing: 4) {
            Text("Holdings")
            Image(systemName: "chevron.down")
                .opacity(
                    vm.state.sortOption == .holdings ||
                    vm.state.sortOption == SortOption.holdingsreversed ? 1 : 0
                )
                .rotationEffect(Angle(degrees: vm.state.sortOption == .holdings ? 0 : 180))
        }
        .onTapGesture {
            withAnimation(.default) {
                vm.updateSortOption(vm.state.sortOption == .holdings ? .holdingsreversed : .holdings)
            }
        }
    }
    
    private var columnTitlePrice: some View {
        HStack(spacing: 4) {
            Text("Price")
                .frame(width: UIScreen.main.bounds.width / 3.5, alignment: .trailing)
            Image(systemName: "chevron.down")
                .opacity(
                    vm.state.sortOption == .price ||
                    vm.state.sortOption == .pricereversed ? 1 : 0
                )
                .rotationEffect(Angle(degrees: vm.state.sortOption == .price ? 0 : 180))
        }
        .onTapGesture {
            withAnimation(.default) {
                vm.updateSortOption(vm.state.sortOption == .price ? .pricereversed : .price)
            }
        }
    }
}
 
