package com.phoenix.analytics.platforms.mixpanel

import com.phoenix.analytics.expectations.AnalyticsContext
import org.koin.core.module.KoinDefinition
import org.koin.core.module.Module


actual fun analyticsMixpanelModule(module: Module, key: String): KoinDefinition<AnalyticsMixpanel>
{
    return module.single { AnalyticsMixpanel(
        AnalyticsContext(
            key,
        get()
    )
    ) }
}