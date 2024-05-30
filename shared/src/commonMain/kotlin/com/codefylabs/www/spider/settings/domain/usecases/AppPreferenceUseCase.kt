package com.codefylabs.www.spider.settings.domain.usecases

import com.codefylabs.www.spider.settings.domain.models.AppPreference
import com.codefylabs.www.spider.settings.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow


interface AppPreferenceUseCase {

    suspend fun updatePreferences(pref: AppPreference)
    suspend fun getAppPreferencesAsFlow(): Flow<AppPreference>
    suspend fun getAppPreferences(): AppPreference

}


class AppPreferenceUseCaseImpl(
    private val repository: SettingsRepository
) : AppPreferenceUseCase {
    override suspend fun updatePreferences(pref: AppPreference) {
        repository.updateAppPreference(pref)
    }

    override suspend fun getAppPreferencesAsFlow(): Flow<AppPreference> {
        return repository.getAppPreferenceAsFlow()
    }

    override suspend fun getAppPreferences(): AppPreference {
        return repository.getAppPreference()
    }

}