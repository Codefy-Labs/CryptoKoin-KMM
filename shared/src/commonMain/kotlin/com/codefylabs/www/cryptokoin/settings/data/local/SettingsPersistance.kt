package com.codefylabs.www.cryptokoin.settings.data.local

import com.codefylabs.www.cryptokoin.settings.data.local.entity.AppPreferenceObject
import com.codefylabs.www.cryptokoin.settings.data.local.entity.PortfolioObject
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface SettingsPersistance {

    suspend fun getAppPreferences(): AppPreferenceObject
    suspend fun getAppPreferencesAsFlow(): Flow<ResultsChange<AppPreferenceObject>>
    suspend fun updateAppPreference(pref: AppPreferenceObject)

    suspend fun getPortfolioAsFlow(): Flow<ResultsChange<PortfolioObject>>
    suspend fun updatePortfolio(coinId: String, amount: Double)
    suspend fun deletePortfolio(coinId: String)
}