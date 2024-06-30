package com.codefylabs.www.canimmigrate.dashboard.domain.models.news

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class News(
    val id: Int,
    val publishedAt: String,
    val source: String,
    val thumbnailUrl: String,
    val detailImageUrl: String,
    val title: String,
    val shortDescription : String,
    val description: String,
    val articleUrl: String? = null,
    @SerialName("view")
    val views: Int,
    @SerialName("share")
    val shares: Int,
    val totalLikes : Int,
    val comments: Int,
    val isTrending : Boolean
)

