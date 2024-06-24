package com.codefylabs.www.canimmigrate.dashboard

import com.codefylabs.www.canimmigrate.dashboard.data.local.DashboardPersistence
import com.codefylabs.www.canimmigrate.dashboard.data.local.DashboardPersistenceImpl
import com.codefylabs.www.canimmigrate.dashboard.data.remote.api.DashboardApi
import com.codefylabs.www.canimmigrate.dashboard.data.remote.api.DashboardApiImpl
import com.codefylabs.www.canimmigrate.dashboard.data.repo.DashboardRepositoryImpl
import com.codefylabs.www.canimmigrate.dashboard.domain.repo.DashboardRepository
import com.codefylabs.www.canimmigrate.dashboard.domain.usecases.TrendingNewsUseCase
import com.codefylabs.www.canimmigrate.dashboard.domain.usecases.TrendingNewsUseCaseImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module


val homeModule = module {

    single<DashboardApi> {
        DashboardApiImpl(get())
    }

    single<DashboardRepository> {
        DashboardRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

    single<DashboardPersistence> {
        DashboardPersistenceImpl(
            Realm.open(
                RealmConfiguration.Builder(
                    schema = setOf()
                ).build()
            )
        )
    }

    single<TrendingNewsUseCase> {
        TrendingNewsUseCaseImpl(get())
    }

}