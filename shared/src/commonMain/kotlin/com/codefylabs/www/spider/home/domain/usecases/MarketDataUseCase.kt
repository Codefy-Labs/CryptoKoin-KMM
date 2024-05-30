package com.codefylabs.www.spider.home.domain.usecases

import com.codefylabs.www.spider.core.util.Either
import com.codefylabs.www.spider.home.domain.models.CoinDetail
import com.codefylabs.www.spider.home.domain.models.MarketData
import com.codefylabs.www.spider.home.domain.repo.HomeRepository



interface  MarketDataUseCase : suspend () -> Either<MarketData>

class MarketDataUseCaseImpl constructor(
    private val repo: HomeRepository
) : MarketDataUseCase {
    override suspend fun invoke(): Either<MarketData> = runCatching {
        val response = repo.getMarketData()
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}