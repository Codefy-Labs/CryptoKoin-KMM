package com.phoenix.analytics.platforms.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.phoenix.analytics.core.AnalyticsEvent
import com.phoenix.analytics.expectations.AnalyticsContext
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


actual class AnalyticsFirebase actual constructor(analyticsContext: AnalyticsContext) :
    KoinComponent {

    private var apiKey: String

    init {
        FirebaseAnalytics.getInstance(analyticsContext.context)
        this.apiKey = analyticsContext.apiToken
    }

    fun getFirebaseAnalytics(): FirebaseAnalytics? {
        return FirebaseAnalytics.getInstance(get() as Context)
    }

    actual fun track(analyticsEvent: AnalyticsEvent) {
        val bundle = Bundle().apply {
            analyticsEvent.eventProperties.getProperties().forEach {
                putString(it.key, it.value)
            }
        }
        getFirebaseAnalytics()?.logEvent(analyticsEvent.eventDesc, bundle)
    }

    actual fun reset() {
        getFirebaseAnalytics()?.resetAnalyticsData()
    }

    actual fun track(eventName: String, properties: Map<String, String>) {

        val bundle = Bundle().apply {
            properties.forEach {
                putString(it.key, it.value)
            }
        }
        getFirebaseAnalytics()?.logEvent(eventName, bundle)
    }

    actual fun setPeopleProperties(properties: Map<String, String>) {
        properties.forEach {
            getFirebaseAnalytics()?.setUserProperty(it.key, it.value)
        }
    }

    private fun Map<String, String>.toJsonObject(): JSONObject {
        return JSONObject().apply {
            this@toJsonObject.forEach { property ->
                (property.key as? String)?.let { put(it, property.value) }
            }
        }
    }
}
