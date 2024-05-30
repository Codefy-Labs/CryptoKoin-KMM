package com.phoenix.analytics.event.log

import com.phoenix.analytics.event.log.events.BaseLogEvent

fun BaseLogEvent.logEvent() {
    this.getEvent().logAnalyticsEvent()
}

fun BaseLogEvent.logEvent(data: Map<String, String>) {
    val analyticsEvent = this.getEvent()
    data.forEach {
        analyticsEvent.eventProperties.add(it.key, it.value)
    }
    analyticsEvent.logAnalyticsEvent()
}