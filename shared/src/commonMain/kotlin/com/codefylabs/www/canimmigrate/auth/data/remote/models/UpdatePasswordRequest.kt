package com.codefylabs.www.canimmigrate.auth.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequest(
    val newPassword: String?,
    val oldPassword: String?
)