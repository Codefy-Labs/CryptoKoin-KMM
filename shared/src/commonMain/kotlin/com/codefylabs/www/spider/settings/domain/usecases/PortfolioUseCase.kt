package com.codefylabs.www.spider.settings.domain.usecases

import com.codefylabs.www.spider.home.domain.repo.HomeRepository
import com.codefylabs.www.spider.settings.data.local.entity.PortfolioObject
import com.codefylabs.www.spider.settings.domain.models.Portfolio
import com.codefylabs.www.spider.settings.domain.repo.SettingsRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface PortfolioUseCase {
    suspend fun getPortfolioAsFlow(): Flow<List<Portfolio>>
    suspend fun updatePortfolio(coinId: String, amount: Double)
    suspend fun deletePortfolio(coinId: String)
}

class PortfolioUseCaseImpl(val repo: SettingsRepository) : PortfolioUseCase {
    override suspend fun getPortfolioAsFlow() = repo.getPortfolioAsFlow()
    override suspend fun updatePortfolio(coinId: String, amount: Double) =
        repo.updatePortfolio(coinId, amount)
    override suspend fun deletePortfolio(coinId: String) = repo.deletePortfolio(coinId)

}