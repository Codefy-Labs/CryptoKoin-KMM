package com.codefylabs.www.canimmigrate

import com.codefylabs.www.canimmigrate.auth.presentation.LoginSharedVM
import com.codefylabs.www.canimmigrate.auth.presentation.signup.SignUpSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.CrsSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.DashboardSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.HomeSharedViewModel
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProcessSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProfileSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProgramsSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding.OnboardingSharedVM
import com.codefylabs.www.canimmigrate.settings.presentation.SettingSharedViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
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

    factory { HomeSharedViewModel(get()) }
    factory { SettingSharedViewModel(get()) }

    factory { ProfileSharedVM() }
    factory { ProgramsSharedVM() }
    factory { ProcessSharedVM() }
    factory { CrsSharedVM() }
    factory { DashboardSharedVM(get()) }
    factory { OnboardingSharedVM(get(), get()) }

    factory { LoginSharedVM(get(), get()) }
    factory { SignUpSharedVM(get()) }
}


object SharedViewModelProvider : KoinComponent {
    fun getHomeViewModel() = getKoin().get<HomeSharedViewModel>()
    fun getCrsViewModel() = getKoin().get<CrsSharedVM>()
    fun getProcessViewModel() = getKoin().get<ProcessSharedVM>()
    fun getProgramsViewModel() = getKoin().get<ProgramsSharedVM>()
    fun getProfileViewModel() = getKoin().get<ProfileSharedVM>()
    fun getDashboardViewModel() = getKoin().get<DashboardSharedVM>()
    fun getOnboardingViewModel() = getKoin().get<OnboardingSharedVM>()
    fun getLoginViewModel() = getKoin().get<LoginSharedVM>()
    fun getSignupViewModel() = getKoin().get<SignUpSharedVM>()
    fun getNotificationSettingViewModel() =
        getKoin().get<SettingSharedViewModel>()
}
