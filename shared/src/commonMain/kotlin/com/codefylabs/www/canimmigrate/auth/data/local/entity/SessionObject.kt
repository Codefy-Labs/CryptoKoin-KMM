package com.codefylabs.www.canimmigrate.auth.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey

class SessionObject : RealmObject {
    @PrimaryKey
    private var objectId: String = "UserSession"
    var userId: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var accessToken: String = ""
    var idToken: String = ""
    var refreshToken: String = ""
}