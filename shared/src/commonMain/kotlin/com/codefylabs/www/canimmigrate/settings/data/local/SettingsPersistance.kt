package com.codefylabs.www.canimmigrate.settings.data.local

import com.codefylabs.www.canimmigrate.settings.data.local.entity.AppPreferenceObject
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface SettingsPersistance {

    suspend fun getAppPreferences(): AppPreferenceObject
    suspend fun getAppPreferencesAsFlow(): Flow<ResultsChange<AppPreferenceObject>>
    suspend fun updateAppPreference(pref: AppPreferenceObject)
}