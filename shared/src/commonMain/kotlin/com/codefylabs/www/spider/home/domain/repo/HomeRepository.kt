package com.codefylabs.www.spider.home.domain.repo

import com.codefylabs.www.spider.home.domain.models.Coin
import com.codefylabs.www.spider.home.domain.models.CoinDetail
import com.codefylabs.www.spider.home.domain.models.MarketData

interface HomeRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinDetail(coinId: String): CoinDetail
    suspend fun getMarketData() : MarketData
}