package com.codefylabs.www.canimmigrate

import com.codefylabs.www.canimmigrate.auth.authModule
import com.codefylabs.www.canimmigrate.core.data.remote.KmmAppKtorClient
import com.codefylabs.www.canimmigrate.dashboard.homeModule
import com.codefylabs.www.canimmigrate.settings.settingsModule
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(
    appModule: Module,
): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            homeModule,
            settingsModule,
            authModule
        )
    }

    val koin = koinApplication.koin

    // doOnStartup is a lambda which is implemented in Swift on iOS side
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

    return koinApplication
}

private val coreModule = module {
    single {
        Json {
            isLenient = true
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    single {
        KmmAppKtorClient(get(), get())
    }


}

expect val platformModule: Module