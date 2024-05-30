//
//  HomeIOSViewModel.swift
//  iosApp
//
//  Created by Shubham Tomar on 13/02/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import Foundation

class HomeIOSViewModel : ObservableObject {
    
    private let viewModel : HomeSharedViewModel
    private var disposableHandle : DisposableHandle?
    
    @Published
    var state: HomeViewState = HomeViewState.companion.initial()
     
    init(sharedViewModel : HomeSharedViewModel){
        self.viewModel = sharedViewModel
        observeEvents()
    }
       
    func observe(){
        self.disposableHandle = viewModel.state.subscribe(onCollect: { newState in
            DispatchQueue.main.async{
                if let state = newState {
                    self.state = state
                    print("State Updated Start")
                    print(state)
                    print("State Updated Finished")
                }
            }
        })
    }
    
    func observeEvents(){
        viewModel.event.subscribe(onCollect: { e in
            if let event = e {
                switch event {
                case let success as HomeEvent.Success :
                    ToastManager.shared.success(message: success.message)
                    
                case let error as HomeEvent.Error :
                    ToastManager.shared.error(message: error.error)
                default: break
                }
            }
        })
    }
     
    func reloadData(){
        viewModel.reloadData()
    }
    
    func updatePortfolio(coin : Coin, amount : Double){
        
    }
    
    func updateSelectedCoin(coin : Coin){
        viewModel.updateSelectedCoin(coin : coin)
    }
    
    func onSearchTextChange(_ text : String){
        viewModel.onSearchTextChange( text : text )
    }
    
    func onCoinsQuantityTextChange(_ text : String){
        viewModel.quantityTextChange( value : text )
    }
    
    func showCheckmark(_ value : Bool){
        viewModel.showCheckMark(value : value)
    }
    
    func removeSelectedCoin(){
        viewModel.updateSelectedCoin(coin : nil)
    }
    
    func updateSortOption(_ sort : SortOption){
        viewModel.updateSortOptions(sort: sort)
    }
    
    func getCurrentValueOfHoldings() -> Double {
       return  viewModel.getCurrentValueOfHoldings()
    }
}
