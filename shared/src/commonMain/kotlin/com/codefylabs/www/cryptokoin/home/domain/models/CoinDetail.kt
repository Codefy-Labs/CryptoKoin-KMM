package com.codefylabs.www.cryptokoin.home.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CoinDetail(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("hashing_algorithm") val hashingAlgorithm: String,
    @SerialName("description") val description: Description,
    @SerialName("block_time_in_minutes") val blockTimeInMinutes: Int,
    @SerialName("links") val links: Links,
)

@Serializable
data class Links(
    @SerialName("homepage") val homepage: List<String>,
    @SerialName("blockchain_site") val blockchainSite: List<String>,
    @SerialName("official_forum_url") val officialForumUrl: List<String>,
    @SerialName("repos_url") val reposUrl: ReposUrl,
    @SerialName("subreddit_url") val subredditUrl: String,
)

@Serializable
data class ReposUrl(
    val bitbucket: List<String>? = null,
    val github: List<String>? = null
)

@Serializable
data class Description(
    val en: String
)