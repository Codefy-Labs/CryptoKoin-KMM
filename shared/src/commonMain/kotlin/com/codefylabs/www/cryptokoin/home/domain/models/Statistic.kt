package com.codefylabs.www.cryptokoin.home.domain.models


data class Statistic(
    val title: String,
    val value: String,
    val percentageChange: Double? = null
)