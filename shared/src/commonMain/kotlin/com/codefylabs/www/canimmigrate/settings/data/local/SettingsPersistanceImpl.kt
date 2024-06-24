package com.codefylabs.www.canimmigrate.settings.data.local

import com.codefylabs.www.canimmigrate.settings.data.local.entity.AppPreferenceObject
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

class SettingsPersistanceImpl(private val db: Realm) : SettingsPersistance {

    override suspend fun getAppPreferences(): AppPreferenceObject {
        return db.query<AppPreferenceObject>().find().lastOrNull() ?: AppPreferenceObject()
    }

    override suspend fun getAppPreferencesAsFlow(): Flow<ResultsChange<AppPreferenceObject>> {
        return db.query<AppPreferenceObject>().asFlow()
    }

    override suspend fun updateAppPreference(pref: AppPreferenceObject) {
        db.writeBlocking {
            copyToRealm(pref, UpdatePolicy.ALL)
        }
    }


}