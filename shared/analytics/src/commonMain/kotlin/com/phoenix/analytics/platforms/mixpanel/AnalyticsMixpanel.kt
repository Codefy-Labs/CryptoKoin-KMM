package com.phoenix.analytics.platforms.mixpanel

import com.phoenix.analytics.core.AnalyticsEvent
import com.phoenix.analytics.expectations.AnalyticsContext

expect class AnalyticsMixpanel(analyticsContext: AnalyticsContext) {
    fun track(analyticsEvent: AnalyticsEvent)
    fun track(eventName:String,  properties:Map<String, String>)
    fun reset()
    fun setPeopleProperties(properties: Map<String, String>)
}
