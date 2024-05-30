package com.phoenix.analytics.platforms.firebase

import com.phoenix.analytics.expectations.AnalyticsContext
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


actual fun analyticsFirebaseModule(
    module: Module,
    firebaseLogAction : FirebaseLogAction
): KoinDefinition<AnalyticsFirebase> {
    return module.single {
        AnalyticsFirebase(
            AnalyticsContext(
                "",
                get()
            )
        )
    }
}