package com.phoenix.analytics.core

import com.phoenix.analytics.event.log.LogCategory
import com.phoenix.analytics.event.log.events.BaseLogEvent
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


data class AnalyticsEvent(
    var eventType: String,
    val eventProperties: AnalyticsEventProperties = AnalyticsEventProperties(),
    val eventTime: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toString(),
    var eventDesc: String,
    val eventLog: BaseLogEvent,
    var eventCategory: LogCategory
) : KoinComponent {

    val appInfo: AppInfo by inject()
    val mixpanel: com.phoenix.analytics.platforms.mixpanel.AnalyticsMixpanel by inject()
    val firebase: com.phoenix.analytics.platforms.firebase.AnalyticsFirebase by inject()

    fun add(key: String, value: String): AnalyticsEvent {
        eventProperties.getProperties()[key] = value
        return this
    }

    fun add(properties: Map<String, String>): AnalyticsEvent {
        eventProperties.getProperties().putAll(properties)
        return this
    }

    fun logAnalyticsEvent() {
        println("logAnalyticsEvent > $this")
//        if (appInfo.isRelease()) {
        mixpanel.track(this)
        firebase.track(this)
//        }
    }
}

