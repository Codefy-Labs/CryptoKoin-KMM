package com.codefylabs.www.canimmigrate.dashboard.domain.models.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewsContainer(
    @SerialName("content")
    val articles: List<News>?,
    val totalElements: Int,
    val totalPages: Int,
    val pageNumber : Int,
    val isLastPage : Boolean,
    val pageSize : Int,
)