package com.codefylabs.www.canimmigrate.auth.data.local.entity

import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class LocalDataObject : RealmObject {
    @PrimaryKey
    var id: String = "LocalData"
    var onboardingSurvey: RealmList<SurveyDataObject> ? = null
    var isLaunchOnboardingFinished: Boolean = false

    companion object {
        fun init() = LocalDataObject()
    }
}

class SurveyDataObject : RealmObject {
    @PrimaryKey
    var key: String = ""
    var question: String = ""
    var answer: String = ""
}