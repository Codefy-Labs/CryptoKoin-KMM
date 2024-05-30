package com.phoenix.analytics.platforms.firebase

import com.phoenix.analytics.core.AnalyticsEvent
import com.phoenix.analytics.expectations.AnalyticsContext



expect class AnalyticsFirebase(analyticsContext: AnalyticsContext) {
    fun track(analyticsEvent: AnalyticsEvent)
    fun track(eventName:String,  properties:Map<String, String>)
    fun reset()
    fun setPeopleProperties(properties: Map<String, String>)
}
