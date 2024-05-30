package com.codefylabs.www.cryptokoin.home.data.remote.api

import com.codefylabs.www.cryptokoin.core.data.remote.KmmAppKtorClient
import com.codefylabs.www.cryptokoin.core.data.remote.get
import com.codefylabs.www.cryptokoin.core.data.remote.toResult
import com.codefylabs.www.cryptokoin.core.util.NetworkResult

class HomeApiImpl(
    private val client: KmmAppKtorClient
) : HomeApi {
    override suspend fun getCoins(): NetworkResult {
        val params = arrayOf(
            "vs_currency" to "usd",
            "order" to "market_cap_desc",
            "per_page" to 100,
            "page" to 1,
            "sparkline" to true,
            "price_change_percentage" to "24h"
        )

        return client.get("https://api.coingecko.com/api/v3/coins/markets", params = params)
            .toResult()
    }

    override suspend fun getCoinDetail(coinId : String): NetworkResult {
        val params = arrayOf(
            "localization" to false,
            "tickers" to false,
            "market_data" to false,
            "community_data" to false,
            "developer_data" to false,
            "sparkline" to false
        )
        return client.get("https://api.coingecko.com/api/v3/coins/$coinId", params = params)
            .toResult()
    }

    override suspend fun getMarketData(): NetworkResult {
        return client.get("https://api.coingecko.com/api/v3/global").toResult()
    }


}