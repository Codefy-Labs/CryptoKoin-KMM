package com.codefylabs.www.canimmigrate.auth.domain.entities

import com.codefylabs.www.canimmigrate.auth.data.local.entity.SessionObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("accessToken") val accessToken: String? = null,
    @SerialName("idToken") val idToken: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("userId") val userId: String? = null,
) {

    companion object {
        fun firstTime() = Session()
    }

}


fun Session.toRealmObject() = SessionObject().also {
    it.userId = this.userId ?: ""
    it.accessToken = this.accessToken ?: ""
    it.idToken = this.idToken ?: ""
    it.refreshToken = this.refreshToken ?: ""
    it.email = this.email ?: ""
    it.phoneNumber = this.phoneNumber ?: ""
}

fun SessionObject.toSession() = Session(
    accessToken = this.accessToken,
    idToken = this.idToken,
    refreshToken = this.refreshToken,
    email = this.email,
    phoneNumber = this.phoneNumber,
    userId = this.userId,
)