package com.codefylabs.www.canimmigrate.auth.domain.models

data class GoogleUser(
    val idToken: String,
    val displayName: String = "",
    val profilePicUrl: String? = null,
)

