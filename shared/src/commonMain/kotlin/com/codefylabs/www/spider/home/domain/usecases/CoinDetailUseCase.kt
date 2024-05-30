package com.codefylabs.www.spider.home.domain.usecases

import com.codefylabs.www.spider.core.util.Either
import com.codefylabs.www.spider.home.domain.models.CoinDetail
import com.codefylabs.www.spider.home.domain.repo.HomeRepository


interface  CoinDetailUseCase : suspend (String) -> Either<CoinDetail>

class  CoinDetailUseCaseImpl constructor(
    private val repo: HomeRepository
) : CoinDetailUseCase {
    override suspend fun invoke(coinId: String): Either<CoinDetail> = runCatching {
        val response = repo.getCoinDetail(coinId = coinId)
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}