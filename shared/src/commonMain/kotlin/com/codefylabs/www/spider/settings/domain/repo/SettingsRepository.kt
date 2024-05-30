package com.codefylabs.www.spider.settings.domain.repo

import com.codefylabs.www.spider.settings.domain.models.AppPreference
import com.codefylabs.www.spider.settings.domain.models.Portfolio
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun updateAppPreference(pref: AppPreference)
    suspend fun getAppPreference(): AppPreference
    suspend fun getAppPreferenceAsFlow(): Flow<AppPreference>

    suspend fun getPortfolioAsFlow(): Flow<List<Portfolio>>
    suspend fun updatePortfolio(coinId: String, amount: Double)
    suspend fun deletePortfolio(coinId: String)
}