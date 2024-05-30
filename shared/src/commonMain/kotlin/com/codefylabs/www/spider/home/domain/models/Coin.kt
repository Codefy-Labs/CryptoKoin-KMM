package com.codefylabs.www.spider.home.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coin(
    @SerialName("ath") val ath: Double? = null,
    @SerialName("ath_change_percentage") val athChangePercentage: Double? = null,
    @SerialName("ath_date") val athDate: String,
    @SerialName("atl") val atl: Double,
    @SerialName("atl_change_percentage") val atlChangePercentage: Double,
    @SerialName("atl_date") val atlDate: String,
    @SerialName("circulating_supply") val circulatingSupply: Double,
    @SerialName("current_price") val currentPrice: Double,
    @SerialName("fully_diluted_valuation") val fullyDilutedValuation: Long? = null,
    @SerialName("high_24h") val high24h: Double,
    @SerialName("id") val id: String,
    @SerialName("image") val image: String,
    @SerialName("last_updated") val lastUpdated: String,
    @SerialName("low_24h") val low24h: Double,
    @SerialName("market_cap") val marketCap: Long,
    @SerialName("market_cap_change_24h") val marketCapChange24h: Double,
    @SerialName("market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double,
    @SerialName("market_cap_rank") val marketCapRank: Int?,
    @SerialName("max_supply") val maxSupply: Double? = null,
    @SerialName("name") val name: String,
    @SerialName("price_change_24h") val priceChange24h: Double,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double? = null,
    @SerialName("price_change_percentage_24h_in_currency") val priceChangePercentage24hInCurrency: Double,
//    @SerialName("roi") val roi: String? = null,
    @SerialName("sparkline_in_7d") val sparklineIn7d: SparklineIn7d,
    @SerialName("symbol") val symbol: String,
    @SerialName("total_supply") val totalSupply: Double? = null,
    @SerialName("total_volume") val totalVolume: Double? = null,
    @SerialName("currentHoldings") var currentHoldings: Double? = null,
) {

    val rank = marketCapRank ?: 0
    val currentHoldingsValue: Double = (currentHoldings ?: 0.0) * currentPrice

    fun updateHoldings(value: Double) = this.copy(currentHoldings = value)

}
