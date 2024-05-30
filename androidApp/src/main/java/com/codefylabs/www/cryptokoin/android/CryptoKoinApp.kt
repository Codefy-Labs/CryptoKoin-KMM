package com.codefylabs.www.cryptokoin.android

import android.app.Application
import android.content.Context
import android.util.Log
import com.codefylabs.www.cryptokoin.core.util.initializeLogger
import com.codefylabs.www.cryptokoin.home.presentation.home.HomeSharedViewModel
import com.codefylabs.www.cryptokoin.initKoin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CryptoKoinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLogger()

        initKoin(module {
            single<Context> { this@CryptoKoinApp }

            viewModel { HomeSharedViewModel(get(), get(), get()) }

            single {
                { Log.i("Startup", "Hello KmpApp, from Android/Kotlin!") }
            }

        })
    }

}