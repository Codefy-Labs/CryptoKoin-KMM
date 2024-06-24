package com.codefylabs.www.canimmigrate.dashboard.domain.usecases

import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.dashboard.domain.repo.DashboardRepository


interface  TrendingNewsUseCase : suspend () -> Either<List<String>>

class  TrendingNewsUseCaseImpl constructor(
    private val repo: DashboardRepository
) :  TrendingNewsUseCase {
    override suspend fun invoke(): Either<List<String>> = runCatching {
        val response = repo.getTrendingNews()
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}