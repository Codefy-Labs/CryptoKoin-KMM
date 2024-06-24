package com.codefylabs.www.canimmigrate.settings.domain.repo

import com.codefylabs.www.canimmigrate.settings.domain.models.AppPreference
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun updateAppPreference(pref: AppPreference)
    suspend fun getAppPreference(): AppPreference
    suspend fun getAppPreferenceAsFlow(): Flow<AppPreference>

}