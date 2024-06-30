package com.codefylabs.www.canimmigrate.android

import android.app.Application
import android.content.Context
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.codefylabs.www.canimmigrate.android.ui.presentation.onboarding.components.OnboardingDataProvider
import com.codefylabs.www.canimmigrate.auth.presentation.LoginSharedVM
import com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword.ForgetPasswordSharedVM
import com.codefylabs.www.canimmigrate.auth.presentation.signup.SignUpSharedVM
import com.codefylabs.www.canimmigrate.core.util.initializeLogger
import com.codefylabs.www.canimmigrate.dashboard.presentation.CrsSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.DashboardSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.DiscussionSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.HomeSharedViewModel
import com.codefylabs.www.canimmigrate.dashboard.presentation.NewsDetailSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProcessSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProfileSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProgramsSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding.OnboardingSharedVM
import com.codefylabs.www.canimmigrate.initKoin
import com.codefylabs.www.canimmigrate.settings.presentation.SettingSharedViewModel
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CanImmigrateApp : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        initializeLogger()
        FirebaseApp.initializeApp(this)
        initKoin(module {
            single<Context> { this@CanImmigrateApp }

            viewModel { HomeSharedViewModel(get()) }
            viewModel { ProfileSharedVM(get()) }
            viewModel { ProcessSharedVM() }
            viewModel { ProgramsSharedVM() }
            viewModel { CrsSharedVM() }
            viewModel { SettingSharedViewModel(get()) }
            viewModel { DashboardSharedVM(get()) }
            viewModel { OnboardingSharedVM(get(), get()) }
            viewModel { LoginSharedVM(get(), get(), get()) }
            viewModel { SignUpSharedVM(get(), get()) }
            viewModel { ForgetPasswordSharedVM(get(), get()) }
            viewModel { DiscussionSharedVM() }
            viewModel { (newsId: String) -> NewsDetailSharedVM(newsId) }

            factory {
                OnboardingDataProvider {
                    androidContext().getString(it)
                }
            }

            single {
                { Log.i("Startup", "Hello KmpApp, from Android/Kotlin!") }
            }

        })
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }



}