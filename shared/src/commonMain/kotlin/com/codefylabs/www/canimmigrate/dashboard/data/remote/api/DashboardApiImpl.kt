package com.codefylabs.www.canimmigrate.dashboard.data.remote.api

import com.codefylabs.www.canimmigrate.core.data.remote.KmmAppKtorClient
import com.codefylabs.www.canimmigrate.core.data.remote.get
import com.codefylabs.www.canimmigrate.core.data.remote.toResult
import com.codefylabs.www.canimmigrate.core.util.NetworkResult

class DashboardApiImpl(
    private val client: KmmAppKtorClient,
) : DashboardApi {
    override suspend fun getTrendingNews(): NetworkResult {
        return client.get("https://api.coingecko.com/api/v3/coins/markets")
            .toResult()
    }

}