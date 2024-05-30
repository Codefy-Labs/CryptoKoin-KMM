package com.codefylabs.www.cryptokoin.home.presentation.home

import com.codefylabs.www.cryptokoin.core.util.Event
import com.codefylabs.www.cryptokoin.core.util.State
import com.codefylabs.www.cryptokoin.home.domain.models.Coin
import com.codefylabs.www.cryptokoin.home.domain.models.MarketData
import com.codefylabs.www.cryptokoin.home.domain.models.Statistic


sealed class HomeEvent : Event {
    data class Success(val message: String) : HomeEvent()
    data class Error(val error: String) : HomeEvent()
}

data class HomeViewState(
    val allCoins: List<Coin> = emptyList(),
    val portfolioCoins: List<Coin> = emptyList(),
    val marketData: MarketData? = null,
    val searchText: String = "",
    val isLoading: Boolean = false,
    val stats: List<Statistic> = emptyList(),
    val sortOption: SortOption = SortOption.Holdings,
    val selectedCoin: Coin? = null,
    val coinsQuantityText: String = "",
    val showCheckmark: Boolean = false
) : State {
    companion object {
        fun initial() = HomeViewState()
    }
}

enum class SortOption {
    Rank, RankReversed,
    Holdings, HoldingsReversed,
    Price, PriceReversed
}
