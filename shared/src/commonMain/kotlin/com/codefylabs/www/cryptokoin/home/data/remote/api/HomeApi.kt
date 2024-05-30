package com.codefylabs.www.cryptokoin.home.data.remote.api

import com.codefylabs.www.cryptokoin.core.util.NetworkResult

interface HomeApi {

    suspend fun getCoins(): NetworkResult

    suspend fun getCoinDetail(coinId: String): NetworkResult

    suspend fun getMarketData(): NetworkResult
}