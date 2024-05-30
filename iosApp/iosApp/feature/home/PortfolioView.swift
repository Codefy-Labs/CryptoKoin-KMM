//
//  PortfolioView.swift
//  CryptoApp
//
//  Created by mk.pwnz on 09.06.2021.
//

import SwiftUI
import shared

struct PortfolioView: View {
    @Environment(\.dismiss) private var dismiss
    @EnvironmentObject private var vm: HomeIOSViewModel
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    SearchBarView(searchText: Binding(get: {
                        vm.state.searchText
                    }, set: { value in
                        vm.onSearchTextChange(value)
                    }))
                    
                    coinLogoList
                    
                    if vm.state.selectedCoin != nil {
                        portfolioInputSection
                    }
                }
            }
            .navigationTitle("Edit portfolio")
            .toolbar(content: {
                ToolbarItem(placement: .navigationBarLeading) {
                    XMarkButton {
                        vm.onSearchTextChange("")
                        self.dismiss()
                    }
                }
                
                ToolbarItem(placement: .navigationBarTrailing) {
                    trailingNavbarButton
                }
            })
        }
        .accentColor(.theme.accent)
        .onChange(of: vm.state.searchText) { value in
            if value == "" {
                vm.removeSelectedCoin()
            }
        }
    }
}

extension PortfolioView {
    private var searchListCoins: [Coin] {
        // if there're coins in portfolio and search string is empty, than will be shown allCoins,
        // otherwise will portfolio coins
        vm.state.searchText.isEmpty && !vm.state.portfolioCoins.isEmpty ? vm.state.portfolioCoins : vm.state.allCoins
    }
    
    private var coinLogoList: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 10) {
                ForEach(searchListCoins, id: \.id) { coin in
                    CoinLogoView(coin: coin)
                        .frame(width: 75)
                        .padding(4)
                        .onTapGesture {
                            withAnimation(.easeIn) {
                                updateSelectedCoins(coin: coin)
                            }
                        }
                        .background(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(vm.state.selectedCoin?.id == coin.id ?  Color.theme.green : .clear, lineWidth: 1))
                }
            }
            .padding(.vertical, 4)
            .padding(.leading)
        }
    }
    
    private var portfolioInputSection: some View {
        VStack(spacing: 20) {
            HStack {
                Text("Current price of \(vm.state.selectedCoin?.symbol.uppercased() ?? ""):")
                
                Spacer()
                
                Text("\(vm.state.selectedCoin?.currentPrice ?? 0.0)")
            }
            
            Divider()
            
            HStack {
                Text("Amount in portfolio: ")
                
                Spacer()
                
                TextField("Ex: 1.4", text: Binding(get: {
                     vm.state.coinsQuantityText
                }, set: { value in
                     vm.onCoinsQuantityTextChange(value)
                }))
                    .multilineTextAlignment(.trailing)
                    .keyboardType(.decimalPad)
            }
            
            Divider()
            
            HStack {
                Text("Current value:")
                
                Spacer()
                
                Text(vm.getCurrentValueOfHoldings().asCurrencyWith2Decimals())
            }
        }
        .animation(.none, value: 0)
        .padding()
        .font(.headline)
    }
    
    private var trailingNavbarButton: some View {
        HStack {
            if vm.state.showCheckmark {
                Image(systemName: "checkmark")
            }
            
//            if let currentHoldings = vm.state.selectedCoin?.currentHoldings as? Double, let coinsQuantity = vm.state.coinsQuantityText.asDouble(), currentHoldings  !=  coinsQuantity {
                Button(action: {
                    saveButtonPressed()
                }, label: {
                    Text("Save".uppercased())
                        .font(.headline)
                })
//            }
        }
    }
    
    private func updateSelectedCoins(coin: Coin) {
        vm.updateSelectedCoin(coin: coin)
        
        if let portfolioCoin = vm.state.portfolioCoins.first(where: { $0.id == coin.id }),
           let amount = portfolioCoin.currentHoldings {
            vm.onCoinsQuantityTextChange( "\(amount)")
        } else {
            vm.onCoinsQuantityTextChange("")

        }
        
    }
    
    private func saveButtonPressed() {
        guard let coin = vm.state.selectedCoin,
              let amount = vm.state.coinsQuantityText.asDouble() else { return }
        
        vm.updatePortfolio(coin: coin, amount: amount)
        
        vm.showCheckmark(true)
        vm.removeSelectedCoin()
        
        // hide keyboard
        UIApplication.shared.endEditing()
        
        // hide checkmark
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            vm.showCheckmark(false)
        }
    }

}


 
