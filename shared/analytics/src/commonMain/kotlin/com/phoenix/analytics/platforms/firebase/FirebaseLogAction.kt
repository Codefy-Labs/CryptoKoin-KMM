package com.phoenix.analytics.platforms.firebase

open class FirebaseLogAction(val log: (eventName: String, properties: Map<String, Any>) -> Unit = { e, map -> }) {

}