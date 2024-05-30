package com.codefylabs.www.spider.home.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SparklineIn7d(
    val price: List<Double>
)