package com.codefylabs.www.spider.android

import android.app.Application
import android.content.Context
import android.util.Log
import com.codefylabs.www.spider.AppSharedViewModel
import com.codefylabs.www.spider.core.util.initializeLogger
import com.codefylabs.www.spider.home.presentation.home.HomeSharedViewModel
import com.codefylabs.www.spider.initKoin
import com.codefylabs.www.spider.settings.presentation.SettingSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class PhoenixApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLogger()

        initKoin(module {
            single<Context> { this@PhoenixApp }

            viewModel { AppSharedViewModel(get()) }
            viewModel { HomeSharedViewModel(get(), get(), get()) }
            viewModel { SettingSharedViewModel(get()) }

            single {
                { Log.i("Startup", "Hello KmpApp, from Android/Kotlin!") }
            }

        })
    }

}