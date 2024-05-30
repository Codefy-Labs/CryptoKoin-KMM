package com.codefylabs.www.spider.home.data.remote.api

import com.codefylabs.www.spider.core.util.NetworkResult

interface HomeApi {

    suspend fun getCoins(): NetworkResult

    suspend fun getCoinDetail(coinId: String): NetworkResult

    suspend fun getMarketData(): NetworkResult
}