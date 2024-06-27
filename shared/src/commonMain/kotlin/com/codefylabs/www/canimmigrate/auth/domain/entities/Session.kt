package com.codefylabs.www.canimmigrate.auth.domain.entities

import com.codefylabs.www.canimmigrate.auth.data.local.entity.SessionObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("token") val accessToken: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("profilePicture") val profilePicture: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("userId") val userId: String? = null,
    @SerialName("name") val name: String? = null,
) {

    fun getNameInitials(): String {
        val words = name?.trim()?.split(" ")
        return if (words.isNullOrEmpty())
            return ""
        else if (words.size == 1) {
            words[0].take(2).uppercase()
        } else {
            (words[0].firstOrNull()?.uppercaseChar().toString() + words[1].firstOrNull()
                ?.uppercaseChar().toString())
        }
    }

    companion object {
        fun empty() = Session()
    }

}


fun Session.toRealmObject() = SessionObject().also {
    it.userId = this.userId ?: ""
    it.accessToken = this.accessToken ?: ""
    it.refreshToken = this.refreshToken ?: ""
    it.email = this.email ?: ""
    it.phoneNumber = this.phoneNumber ?: ""
    it.name = this.name ?: ""
    it.profilePicture = this.profilePicture
}

fun SessionObject.toSession() = Session(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken,
    email = this.email,
    phoneNumber = this.phoneNumber,
    userId = this.userId,
    name = this.name,
    profilePicture = this.profilePicture
)