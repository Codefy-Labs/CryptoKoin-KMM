package com.codefylabs.www.spider.settings

import com.codefylabs.www.spider.settings.data.local.SettingsPersistance
import com.codefylabs.www.spider.settings.data.local.SettingsPersistanceImpl
import com.codefylabs.www.spider.settings.data.local.entity.AppPreferenceObject
import com.codefylabs.www.spider.settings.data.local.entity.PortfolioObject
import com.codefylabs.www.spider.settings.data.remote.api.SettingsApi
import com.codefylabs.www.spider.settings.data.remote.api.SettingsApiImpl
import com.codefylabs.www.spider.settings.data.repo.SettingsRepositoryImpl
import com.codefylabs.www.spider.settings.domain.repo.SettingsRepository
import com.codefylabs.www.spider.settings.domain.usecases.AppPreferenceUseCase
import com.codefylabs.www.spider.settings.domain.usecases.AppPreferenceUseCaseImpl
import com.codefylabs.www.spider.settings.domain.usecases.PortfolioUseCase
import com.codefylabs.www.spider.settings.domain.usecases.PortfolioUseCaseImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val settingsModule = module {

    single<SettingsPersistance> {
        SettingsPersistanceImpl(
            Realm.open(
                RealmConfiguration.Builder(
                    schema = setOf(AppPreferenceObject::class, PortfolioObject::class)
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

    single<PortfolioUseCase> {
        PortfolioUseCaseImpl(get())
    }
}