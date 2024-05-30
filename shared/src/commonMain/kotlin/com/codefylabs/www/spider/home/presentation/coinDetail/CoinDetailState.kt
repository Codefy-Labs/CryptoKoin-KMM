package com.codefylabs.www.spider.home.presentation.coinDetail

import com.codefylabs.www.spider.core.util.Event
import com.codefylabs.www.spider.core.util.State
import com.codefylabs.www.spider.home.domain.models.Coin
import com.codefylabs.www.spider.home.domain.models.Statistic


data class CoinDetailState(
    val coin: Coin? = null,
    val overviewStatistic: List<Statistic> = emptyList(),
    val detailStatistic: List<Statistic> = emptyList(),
    val coinDescription: String? = null,
    val websiteURL: String? = null,
    val redditURL: String? = null,
    val showPortfolioView: Boolean = false
) : State {
    companion object {
        fun initial() = CoinDetailState()
    }
}


sealed class CoinDetailEvent : Event {
    data class Success(val message: String) : CoinDetailEvent()
    data class Error(val error: String) : CoinDetailEvent()
}