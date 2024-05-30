package com.codefylabs.www.cryptokoin.settings.data.repo

import com.codefylabs.www.cryptokoin.settings.data.local.SettingsPersistance
import com.codefylabs.www.cryptokoin.settings.data.remote.api.SettingsApi
import com.codefylabs.www.cryptokoin.settings.domain.models.AppPreference
import com.codefylabs.www.cryptokoin.settings.domain.models.Portfolio
import com.codefylabs.www.cryptokoin.settings.domain.models.toAppPreference
import com.codefylabs.www.cryptokoin.settings.domain.models.toPortfolio
import com.codefylabs.www.cryptokoin.settings.domain.models.toRealmObject
import com.codefylabs.www.cryptokoin.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class SettingsRepositoryImpl(
    private val json: Json,
    private val persistance: SettingsPersistance,
    private val api: SettingsApi,
) : SettingsRepository {

    override suspend fun updateAppPreference(pref: AppPreference) {
        persistance.updateAppPreference(pref.toRealmObject())
    }

    override suspend fun getAppPreference(): AppPreference {
        return persistance.getAppPreferences().toAppPreference()
    }

    override suspend fun getAppPreferenceAsFlow(): Flow<AppPreference> {
        return persistance.getAppPreferencesAsFlow()
            .map { it.list.lastOrNull()?.toAppPreference() ?: AppPreference() }
    }

    override suspend fun getPortfolioAsFlow(): Flow<List<Portfolio>> {
        return persistance.getPortfolioAsFlow().map { it.list.map { it.toPortfolio() } }
    }

    override suspend fun updatePortfolio(coinId: String, amount: Double) {
        persistance.updatePortfolio(coinId, amount)
    }

    override suspend fun deletePortfolio(coinId: String) {
        persistance.deletePortfolio(coinId)
    }

}