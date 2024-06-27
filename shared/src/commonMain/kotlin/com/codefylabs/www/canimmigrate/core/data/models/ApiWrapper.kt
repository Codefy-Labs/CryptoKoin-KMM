package com.codefylabs.www.canimmigrate.core.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiWrapper<T>(
    val message: String? = null,
    val data: T? = null,
    val status: Boolean? = null
)