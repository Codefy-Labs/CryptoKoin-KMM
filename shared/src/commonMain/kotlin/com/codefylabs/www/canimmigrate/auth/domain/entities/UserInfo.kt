package com.codefylabs.www.canimmigrate.auth.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: String?,
    val userName: String?,
    val email: String?,
    val phone_number: String?,
)

