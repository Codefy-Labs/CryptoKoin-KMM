package com.codefylabs.www.spider.home.data.repo

import com.codefylabs.www.spider.core.util.NetworkResult
import com.codefylabs.www.spider.core.util.toObject
import com.codefylabs.www.spider.home.data.local.HomePersistence
import com.codefylabs.www.spider.home.data.remote.api.HomeApi
import com.codefylabs.www.spider.home.domain.models.Coin
import com.codefylabs.www.spider.home.domain.models.CoinDetail
import com.codefylabs.www.spider.home.domain.models.GlobalData
import com.codefylabs.www.spider.home.domain.models.MarketData
import com.codefylabs.www.spider.home.domain.repo.HomeRepository
import kotlinx.serialization.json.Json

class HomeRepositoryImpl(
    private val api: HomeApi,
    private val json: Json,
    private val homePersistence: HomePersistence
) : HomeRepository {
    override suspend fun getCoins(): List<Coin> {
        return when (val result = api.getCoins()) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> json.toObject<List<Coin>>(result.data.decodeToString())
        }
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetail {
        return when (val result = api.getCoinDetail(coinId)) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> json.toObject<CoinDetail>(result.data.decodeToString())
        }
    }

    override suspend fun getMarketData(): MarketData {
        return when (val result = api.getMarketData()) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> json.toObject<GlobalData>(result.data.decodeToString()).data
        }
    }

}