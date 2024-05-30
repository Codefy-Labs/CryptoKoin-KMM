package com.codefylabs.www.spider.home.presentation.coinDetail


import com.codefylabs.www.spider.core.util.StateViewModel
import com.codefylabs.www.spider.core.util.ViewModel
import com.codefylabs.www.spider.home.domain.models.Coin
import com.codefylabs.www.spider.home.domain.models.CoinDetail
import com.codefylabs.www.spider.home.domain.models.Statistic
import com.codefylabs.www.spider.home.domain.usecases.CoinDetailUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.cancel
//
//class CoinDetailViewModel(val coinDetailUseCase: CoinDetailUseCase): StateViewModel<CoinDetailEvent, CoinDetailState>(CoinDetailState.initial()){
//
//
//    init {
//        addSubscribers()
//    }
//
//    private fun addSubscribers() {
//        coinDetailService.coinDetail.combine(coinDetailService.coin) { coinDetail, coin ->
//            mapDataToStatistic(coinDetail, coin)
//        }.onEach { returnedArrays ->
//            _state.value = _state.value.copy(
//                overviewStatistic = returnedArrays.first,
//                detailStatistic = returnedArrays.second
//            )
//        }.launchIn(viewModelScope)
//
//        coinDetailService.coinDetail.onEach { coinDetail ->
//            _state.value = _state.value.copy(
//                coinDescription = coinDetail?.readableDescription ?: "N/A",
//                websiteURL = coinDetail?.links?.homepage?.firstOrNull() ?: "N/A",
//                redditURL = coinDetail?.links?.subredditURL ?: "N/A"
//            )
//        }.launchIn(viewModelScope)
//    }
//
//    private fun mapDataToStatistic(
//        coinDetail: CoinDetail?,
//        coin: Coin
//    ): Pair<List<Statistic>, List<Statistic>> {
//        val overview = createOverview(coinDetail)
//        val details = createDetailsForCoin(coin, coinDetail)
//        return Pair(overview, details)
//    }
//
//    private fun createOverview(coinDetail: CoinDetail?): List<Statistic> {
//        val price =
//            if (coin.currentPrice > 10) coin.currentPrice.asCurrencyWith2Decimals() else coin.currentPrice.asCurrencyWith6Decimals()
//        val pricePercentChange = coin.priceChangePercentage24H
//        val priceStat = Statistic("Current Price", price, pricePercentChange)
//
//        val marketCap = "$" + (coin.marketCap?.formattedWithAbbreviations() ?: "")
//        val marketCapPercentChange = coin.marketCapChangePercentage24H
//        val marketCapStat = Statistic("Market Cap", marketCap, marketCapPercentChange)
//
//        val rank = coin.rank.toString()
//        val rankStat = Statistic("Rank", rank)
//
//        val volume = "$" + (coin.totalVolume?.formattedWithAbbreviations() ?: "")
//        val volumeStat = Statistic("Volume", volume)
//
//        return listOf(priceStat, marketCapStat, rankStat, volumeStat)
//    }
//
//    private fun createDetailsForCoin(  coinDetail: CoinDetail?): List<Statistic> {
//        val high = if ((coin.high24H ?: 0.0) > 10) coin.high24H?.asCurrencyWith2Decimals()
//            ?: "N/A" else coin.high24H?.asCurrencyWith6Decimals() ?: "N/A"
//        val highStat = Statistic("High 24h", high)
//
//        val low = if ((coin.low24H ?: 0.0) > 10) coin.low24H?.asCurrencyWith2Decimals()
//            ?: "N/A" else coin.low24H?.asCurrencyWith6Decimals() ?: "N/A"
//        val lowStat = Statistic("Low 24h", low)
//
//        val priceChange =
//            if ((coin.priceChange24H ?: 0.0) > 10) coin.priceChange24H?.asCurrencyWith2Decimals()
//                ?: "N/A" else coin.priceChange24H?.asCurrencyWith6Decimals() ?: "N/A"
//        val pricePercentChange = coin.priceChangePercentage24H
//        val priceChangeStat = Statistic("24h Price Change", priceChange, pricePercentChange)
//
//        val marketCapChange = "$" + (coin.marketCapChange24H?.formattedWithAbbreviations() ?: "")
//        val marketCapPercentChange = coin.marketCapChangePercentage24H
//        val marketCapChangeStat =
//            Statistic("24h Market Cap Change", marketCapChange, marketCapPercentChange)
//
//        val blockTime = coinDetail?.blockTimeInMinutes ?: 0
//        val blockTimeString = if (blockTime == 0) "N/A" else blockTime.toString()
//        val blockTimeStat = Statistic("Block Time", blockTimeString)
//
//        val hashingAlgo = coinDetail?.hashingAlgorithm ?: "N/A"
//        val hashingAlgoStat = Statistic("Hashing Algorithm", hashingAlgo)
//
//        return listOf(
//            highStat,
//            lowStat,
//            priceChangeStat,
//            marketCapChangeStat,
//            blockTimeStat,
//            hashingAlgoStat
//        )
//    }
//
//    fun onCleared() {
//        viewModelScope.cancel()
//    }
//}