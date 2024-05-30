package com.codefylabs.www.cryptokoin.home.domain.usecases

import com.codefylabs.www.cryptokoin.core.util.Either
import com.codefylabs.www.cryptokoin.home.domain.models.Coin
import com.codefylabs.www.cryptokoin.home.domain.repo.HomeRepository


interface  CoinsUseCase : suspend () -> Either<List<Coin>>

class  CoinsUseCaseImpl constructor(
    private val repo: HomeRepository
) :  CoinsUseCase {
    override suspend fun invoke(): Either<List<Coin>> = runCatching {
        val response = repo.getCoins()
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}