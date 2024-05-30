package com.codefylabs.www.spider.home.presentation.home

import com.codefylabs.www.spider.core.util.Either
import com.codefylabs.www.spider.core.util.StateViewModel
import com.codefylabs.www.spider.core.util.asCurrencyWith2Decimals
import com.codefylabs.www.spider.home.domain.models.Coin
import com.codefylabs.www.spider.home.domain.models.MarketData
import com.codefylabs.www.spider.home.domain.models.Statistic
import com.codefylabs.www.spider.home.domain.usecases.CoinsUseCase
import com.codefylabs.www.spider.home.domain.usecases.MarketDataUseCase
import com.codefylabs.www.spider.settings.domain.models.Portfolio
import com.codefylabs.www.spider.settings.domain.usecases.PortfolioUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeSharedViewModel(
    private val marketDataUseCase: MarketDataUseCase,
    private val coinsUseCase: CoinsUseCase,
    private val portfolioUseCase: PortfolioUseCase
) : StateViewModel<HomeEvent, HomeViewState>(HomeViewState.initial()) {

    init {
        addSubscribers()
        reloadData()
    }

    fun onSearchTextChange(text: String) {
        updateState(newState = state.value.copy(searchText = text))
    }

    fun updateSortOptions(sort: SortOption) {
        updateState(newState = state.value.copy(sortOption = sort))
    }

    fun reloadData() {
        coroutine.launch {
            updateState(state.value.copy(isLoading = true))

            when (val result = coinsUseCase()) {
                is Either.Error -> {
                    Napier.e("FetchCoinsResult Error -> ${result.message}")
                }

                is Either.Success -> {
                    Napier.i("FetchCoinsResult -> ${result.data}")
                    updateState(newState = state.value.copy(allCoins = result.data))
                }
            }

            when (val result = marketDataUseCase()) {
                is Either.Error -> {
                    Napier.e("MarketDataUse Error -> ${result.message}")
                }

                is Either.Success -> {
                    Napier.i("MarketDataUse -> ${result.data}")
                    updateState(newState = state.value.copy(marketData = result.data))
                }
            }
        }
    }


    fun getCurrentValueOfHoldings(): Double {
        val quantity = state.value.coinsQuantityText.toDoubleOrNull() ?: 0.0
        return quantity.times((state.value.selectedCoin?.currentPrice ?: 0.0))
    }

    fun updateSelectedCoin(coin: Coin?) {
        updateState(state.value.copy(selectedCoin = coin))
    }

    fun quantityTextChange(value: String) {
        updateState(state.value.copy(coinsQuantityText = value))
    }

    fun showCheckMark(value: Boolean) {
        updateState(state.value.copy(showCheckmark = value))
    }

    @OptIn(FlowPreview::class)
    private fun addSubscribers() {
        coroutine.launch {

            combine(
                state.map { it.searchText }.debounce(500),
                state.map { it.allCoins },
                state.map { it.sortOption }
            ) { searchText, allCoins, sortOption ->
                filterAndSortCoins(searchText, allCoins, sortOption)
            }.collect { filteredCoins ->
                updateState(state.value.copy(allCoins = filteredCoins))
            }

            combine(
                state.map { it.allCoins },
                portfolioUseCase.getPortfolioAsFlow()
            ) { allCoins, portfolioEntities ->
                mapAllCoinsToPortfolioCoins(allCoins, portfolioEntities)
            }.collect { portfolioCoins ->
                updateState(
                    state.value.copy(portfolioCoins = sortPortfolioCoinsIfNeeded(portfolioCoins))
                )
            }

            combine(
                state.map { it.marketData },
                state.map { it.portfolioCoins }
            ) { marketData, portfolioCoins ->
                mapGlobalMarketData(marketData, portfolioCoins)
            }.collect { stats ->
                updateState(state.value.copy(stats = stats, isLoading = false))
            }

        }
    }

    private fun sortCoins(sort: SortOption, coins: List<Coin>): List<Coin> {
        return when (sort) {
            SortOption.Rank -> coins.sortedBy { it.rank }
            SortOption.RankReversed -> coins.sortedByDescending { it.rank }
            SortOption.Holdings -> coins.sortedBy { it.currentHoldingsValue }
            SortOption.HoldingsReversed -> coins.sortedByDescending { it.currentHoldingsValue }
            SortOption.Price -> coins.sortedBy { it.currentPrice }
            SortOption.PriceReversed -> coins.sortedByDescending { it.currentPrice }
        }
    }

    private fun sortPortfolioCoinsIfNeeded(coins: List<Coin>): List<Coin> {
        return when (state.value.sortOption) {
            SortOption.Holdings -> coins.sortedBy { it.currentHoldingsValue }
            else -> coins.sortedByDescending { it.currentHoldingsValue }
        }
    }

    private fun filterAndSortCoins(
        searchText: String,
        coins: List<Coin>,
        sortOption: SortOption
    ): List<Coin> {
        val filteredCoins = filterCoins(searchText, coins)
        return sortCoins(sortOption, filteredCoins)
    }

    private fun mapAllCoinsToPortfolioCoins(
        allCoins: List<Coin>,
        portfolioEntities: List<Portfolio>
    ): List<Coin> {
        return allCoins.mapNotNull { coin ->
            portfolioEntities.find { it.coinId == coin.id }?.let {
                coin.updateHoldings(it.amount)
            }
        }
    }

    private fun filterCoins(searchText: String, coins: List<Coin>): List<Coin> {
        if (searchText.isEmpty()) return coins
        val lowercasedText = searchText.lowercase()
        return coins.filter {
            it.name.lowercase().contains(lowercasedText) ||
                    it.symbol.lowercase().contains(lowercasedText) ||
                    it.id.lowercase().contains(lowercasedText)
        }
    }

    private fun mapGlobalMarketData(
        marketData: MarketData?,
        portfolioCoins: List<Coin>
    ): List<Statistic> {
        val stats = mutableListOf<Statistic>()
        marketData?.let { data ->
            val marketCap =
                Statistic("Market Cap", data.marketCap, data.marketCapChangePercentage24HUsd)
            val volume = Statistic("24h Volume", data.volume)
            val btcDominance = Statistic("BTC Dominance", data.btcDominance)

            val portfolioValue = portfolioCoins.sumOf { it.currentHoldingsValue }
            val previousValue = portfolioCoins.sumOf {
                val currentValue = it.currentHoldingsValue
                val percentChange = it.priceChangePercentage24h ?: (0.0 / 100.0)
                currentValue / (1 + percentChange)
            }
            val percentageChange = (portfolioValue - previousValue) / previousValue
            val portfolio =
                Statistic(
                    "Portfolio Value",
                    portfolioValue.asCurrencyWith2Decimals(),
                    percentageChange
                )

            stats.addAll(listOf(marketCap, volume, btcDominance, portfolio))
        }
        return stats
    }
}

