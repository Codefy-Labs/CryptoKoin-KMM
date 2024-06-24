package com.codefylabs.www.canimmigrate.auth.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class ForgetPasswordRequest(
    val email: String
)