package com.phoenix.analytics.platforms.firebase


import com.phoenix.analytics.core.AnalyticsEvent
import com.phoenix.analytics.expectations.AnalyticsContext

actual class AnalyticsFirebase actual constructor(analyticsContext: AnalyticsContext) {

    private var firebaseLogAction: FirebaseLogAction? = null

    fun setLogAction(firebaseLogAction: FirebaseLogAction){
        this.firebaseLogAction = firebaseLogAction
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
        firebaseLogAction?.log?.let {
            it(
                analyticsEvent.eventDesc,
                analyticsEvent.eventProperties.getProperties().toMap()
            )
        }
    }

    actual fun track(eventName: String, properties: Map<String, String>) {
        firebaseLogAction?.log?.let {
            it(eventName, properties)
        }
    }

    actual fun reset() {

    }

    actual fun setPeopleProperties(properties: Map<String, String>) {

    }

}
