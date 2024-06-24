package com.codefylabs.www.canimmigrate.settings.domain.models

import com.codefylabs.www.canimmigrate.settings.data.local.entity.AppPreferenceObject

data class AppPreference(
    var isDarkMode: Boolean = false
)

fun AppPreference.toRealmObject() = AppPreferenceObject().also {
    it.isDarkMode = this.isDarkMode
}

fun AppPreferenceObject.toAppPreference() = AppPreference(isDarkMode = isDarkMode)