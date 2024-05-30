package com.phoenix.analytics.platforms.mixpanel

import com.phoenix.analytics.core.AnalyticsEvent
import com.phoenix.analytics.expectations.AnalyticsContext
import kotlinx.cinterop.ExperimentalForeignApi

actual class AnalyticsMixpanel actual constructor(analyticsContext: AnalyticsContext) {

    init {
//        Mixpanel.sharedInstanceWithToken(analyticsContext.apiToken,true)
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
//        Mixpanel.sharedInstance()?.track(
//            analyticsEvent.eventDesc,
//            analyticsEvent.eventProperties.getProperties().toMap()
//        )
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun track(eventName:String, properties:Map<String, String>)
    {
//        Mixpanel.sharedInstance()?.track(eventName,properties.toMap())
//        print("In this Mixpanel (${getMixpanel().toString()})_, event tracked")
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun reset() {
//        Mixpanel.sharedInstance()?.reset()
    }

    actual fun setPeopleProperties(properties: Map<String, String>)
    {
//       Mixpanel.sharedInstance()?.people?.set(properties.toMap())
    }



}
