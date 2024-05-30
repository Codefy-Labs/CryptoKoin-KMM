package com.codefylabs.www.spider.home.domain.models


data class Statistic(
    val title: String,
    val value: String,
    val percentageChange: Double? = null
)