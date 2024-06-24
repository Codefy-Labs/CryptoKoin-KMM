package com.codefylabs.www.canimmigrate.auth.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val name : String,
    val email : String,
    val password : String
)