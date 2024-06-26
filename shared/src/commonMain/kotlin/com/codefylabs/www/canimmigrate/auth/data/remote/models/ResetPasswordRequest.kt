package com.codefylabs.www.canimmigrate.auth.data.remote.models

import kotlinx.serialization.Serializable


@Serializable
data class ResetPasswordRequest(
    val email: String,
    val password: String,
    val otp: String
)