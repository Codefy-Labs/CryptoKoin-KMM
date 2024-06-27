package com.codefylabs.www.canimmigrate.dashboard.domain.models

data class Feed(
    val imageRes: String,
    val title: String,
    val daysToGo: String,
    val views: String,
    val shares: String,
    val id: String = (Int.MIN_VALUE..Int.MAX_VALUE).random().toString(),
    val url : String? = null
)
