package com.codefylabs.www.canimmigrate.settings

import com.codefylabs.www.canimmigrate.settings.data.local.SettingsPersistance
import com.codefylabs.www.canimmigrate.settings.data.local.SettingsPersistanceImpl
import com.codefylabs.www.canimmigrate.settings.data.local.entity.AppPreferenceObject
import com.codefylabs.www.canimmigrate.settings.data.remote.api.SettingsApi
import com.codefylabs.www.canimmigrate.settings.data.remote.api.SettingsApiImpl
import com.codefylabs.www.canimmigrate.settings.data.repo.SettingsRepositoryImpl
import com.codefylabs.www.canimmigrate.settings.domain.repo.SettingsRepository
import com.codefylabs.www.canimmigrate.settings.domain.usecases.AppPreferenceUseCase
import com.codefylabs.www.canimmigrate.settings.domain.usecases.AppPreferenceUseCaseImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val settingsModule = module {

    single<SettingsPersistance> {
        SettingsPersistanceImpl(
            Realm.open(
                RealmConfiguration.Builder(
                    schema = setOf(AppPreferenceObject::class)
                ).build()
            )
        )
    }

    single<SettingsApi> {
        SettingsApiImpl(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get(), get())
    }

    single<AppPreferenceUseCase> {
        AppPreferenceUseCaseImpl(get())
    }

}