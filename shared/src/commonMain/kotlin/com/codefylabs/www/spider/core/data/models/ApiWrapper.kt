package com.codefylabs.www.spider.core.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiWrapper<T>(
    val message: String? = null,
    val response: T? = null,
    val status: String? = null
)