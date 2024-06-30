package com.codefylabs.www.canimmigrate.dashboard.domain.usecases

import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.dashboard.domain.models.news.News
import com.codefylabs.www.canimmigrate.dashboard.domain.models.news.NewsContainer
import com.codefylabs.www.canimmigrate.dashboard.domain.repo.DashboardRepository

interface NewsUseCase {
    suspend fun execute(category: String, page: Int): Either<NewsContainer>
}

class NewsUseCaseImpl constructor(
    private val repo: DashboardRepository,
) : NewsUseCase {
    override suspend fun execute(category: String, page: Int): Either<NewsContainer> =
        kotlin.runCatching {
            val data = repo.getNews(page = page, category = category, size = 20)
            Either.Success(data)
        }.getOrElse {
            it.printStackTrace()
            Either.Error("Something went wrong!!")
        }

}