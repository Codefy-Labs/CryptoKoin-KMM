package com.codefylabs.www.cryptokoin.settings.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class AppPreferenceObject : RealmObject {

    @PrimaryKey
    private var id = 0
    var isDarkMode: Boolean = false
}