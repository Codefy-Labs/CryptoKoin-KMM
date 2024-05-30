package com.phoenix.analytics.platforms.firebase

import com.phoenix.analytics.platforms.mixpanel.AnalyticsMixpanel
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


expect fun analyticsFirebaseModule(module: Module, firebaseLogAction : FirebaseLogAction): KoinDefinition<AnalyticsFirebase>

