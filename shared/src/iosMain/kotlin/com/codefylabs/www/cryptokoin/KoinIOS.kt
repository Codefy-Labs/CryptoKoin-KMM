package com.codefylabs.www.cryptokoin

import com.codefylabs.www.cryptokoin.home.presentation.home.HomeSharedViewModel
import com.codefylabs.www.cryptokoin.settings.presentation.SettingSharedViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

fun initKoinIos(
    doOnStartup: () -> Unit,
): KoinApplication = initKoin(
    module {
        single { doOnStartup }
    }
)


actual val platformModule = module {

    single<HttpClient> {
        HttpClient(Darwin) {
            engine {
                configureRequest {
                    setAllowsCellularAccess(true)
                }
            }
        }

    }

    factory { HomeSharedViewModel(get(), get(), get()) }
    factory { SettingSharedViewModel(get()) }

}


object SharedViewModelProvider : KoinComponent {
    fun getHomeViewModel() = getKoin().get<HomeSharedViewModel>()
    fun getNotificationSettingViewModel() = getKoin().get<SettingSharedViewModel>()
}
