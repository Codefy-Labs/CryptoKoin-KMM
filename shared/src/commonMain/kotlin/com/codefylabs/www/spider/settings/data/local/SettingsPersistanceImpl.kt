package com.codefylabs.www.spider.settings.data.local

import com.codefylabs.www.spider.settings.data.local.entity.AppPreferenceObject
import com.codefylabs.www.spider.settings.data.local.entity.PortfolioObject
import com.codefylabs.www.spider.settings.domain.models.Portfolio
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

    override suspend fun getPortfolioAsFlow(): Flow<ResultsChange<PortfolioObject>> {
        return db.query<PortfolioObject>().asFlow()
    }

    override suspend fun updatePortfolio(coinId: String, amount: Double) {
        db.writeBlocking {
            copyToRealm(PortfolioObject().also {
                it.coinId = coinId
                it.amount = amount
            }, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun deletePortfolio(coinId: String) {
      db.writeBlocking {
          val objectToDelete: PortfolioObject? = query<PortfolioObject>("coinId == $0", coinId).first().find()
          if (objectToDelete != null) {
              delete(objectToDelete)
          }
      }
    }

}