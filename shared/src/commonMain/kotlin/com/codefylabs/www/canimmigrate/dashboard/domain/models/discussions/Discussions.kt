package com.codefylabs.www.canimmigrate.dashboard.domain.models.discussions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Discussions(
    @SerialName("news_id")
    val newsId: Int,
    val currentPage: Int,
    val totalPages: Int,
    val totalComments: Int,
    val comments: List<Comment>
)

@Serializable
data class Comment(
    val id: Int,
    val likes: Int,
    val comment: String,
    val timestamp: String,
    val user: User,
    val replies: List<Reply>
)

@Serializable
data class User(
    val name: String,
    val profilePicture: String
)

@Serializable
data class Reply(
    val id: Int,
    val comment: String,
    val likes: Int,
    val timestamp: String,
    val user: User
)