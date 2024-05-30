package com.codefylabs.www.cryptokoin.home.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.pow
import kotlin.math.round


@Serializable
data class GlobalData(
    val data: MarketData
)

@Serializable
data class MarketData(

    @SerialName("total_market_cap")
    val totalMarketCap: TotalMarketCap ? = null,
    @SerialName("total_volume")
    val totalVolume: TotalVolume? = null,
    @SerialName("market_cap_percentage")
    val marketCapPercentage: MarketCapPercentage? = null,
    @SerialName("market_cap_change_percentage_24h_usd")
    val marketCapChangePercentage24HUsd: Double? = null
) {

    val marketCap: String
        get() {
            val item = totalMarketCap?.usd ?: 0.0
            return "$" + item.formattedWithAbbreviations()
        }

    val volume: String
        get() {
            val item = totalVolume?.usd ?: 0.0
            return "$" + item.formattedWithAbbreviations()
        }

    val btcDominance: String
        get() {
            val item = marketCapPercentage?.btc ?: 0.0
            return item.asPercentString() ?: ""
        }

    fun Double.formattedWithAbbreviations(): String {
        return when {
            this >= 1_000_000_000 -> "${(this / 1_000_000_000).toFixed(2)}B"
            this >= 1_000_000 -> "${(this / 1_000_000).toFixed(2)}M"
            this >= 1_000 -> "${(this / 1_000).toFixed(2)}K"
            else -> this.toString()
        }
    }

    fun Double.asPercentString(): String {
        return "${this.toFixed(2)}%"
    }

    fun Double.toFixed(digits: Int): String {
        val factor = 10.0.pow(digits)
        return (round(this * factor) / factor).toString()
    }
}

@Serializable
data class MarketCapPercentage(
    val btc: Double = 0.0,
    val eth: Double = 0.0,
    val usdt: Double = 0.0,
    val bnb: Double = 0.0,
    val sol: Double = 0.0,
    val steth: Double = 0.0,
    val usdc: Double = 0.0,
    val xrp: Double = 0.0,
    val doge: Double = 0.0,
    val ton: Double = 0.0
)
@Serializable
data class TotalVolume(
    val aed: Double = 0.0,
    val ars: Double = 0.0,
    val aud: Double = 0.0,
    val bch: Double = 0.0,
    val bdt: Double = 0.0,
    val bhd: Double = 0.0,
    val bits: Double = 0.0,
    val bmd: Double = 0.0,
    val bnb: Double = 0.0,
    val brl: Double = 0.0,
    val btc: Double = 0.0,
    val cad: Double = 0.0,
    val chf: Double = 0.0,
    val clp: Double = 0.0,
    val cny: Double = 0.0,
    val czk: Double = 0.0,
    val dkk: Double = 0.0,
    val dot: Double = 0.0,
    val eos: Double = 0.0,
    val eth: Double = 0.0,
    val eur: Double = 0.0,
    val gbp: Double = 0.0,
    val gel: Double = 0.0,
    val hkd: Double = 0.0,
    val huf: Double = 0.0,
    val idr: Double = 0.0,
    val ils: Double = 0.0,
    val inr: Double = 0.0,
    val jpy: Double = 0.0,
    val krw: Double = 0.0,
    val kwd: Double = 0.0,
    val link: Double = 0.0,
    val lkr: Double = 0.0,
    val ltc: Double = 0.0,
    val mmk: Double = 0.0,
    val mxn: Double = 0.0,
    val myr: Double = 0.0,
    val ngn: Double = 0.0,
    val nok: Double = 0.0,
    val nzd: Double = 0.0,
    val php: Double = 0.0,
    val pkr: Double = 0.0,
    val pln: Double = 0.0,
    val rub: Double = 0.0,
    val sar: Double = 0.0,
    val sats: Double = 0.0,
    val sek: Double = 0.0,
    val sgd: Double = 0.0,
    val thb: Double = 0.0,
    val twd: Double = 0.0,
    val uah: Double = 0.0,
    val usd: Double = 0.0,
    val vef: Double = 0.0,
    val vnd: Double = 0.0,
    val xag: Double = 0.0,
    val xau: Double = 0.0,
    val xdr: Double = 0.0,
    val xlm: Double = 0.0,
    val xrp: Double = 0.0,
    val yfi: Double = 0.0,
    val zar: Double = 0.0
)

@Serializable
data class TotalMarketCap(
    val aed: Double = 0.0,
    val ars: Double = 0.0,
    val aud: Double = 0.0,
    val bch: Double = 0.0,
    val bdt: Double = 0.0,
    val bhd: Double = 0.0,
    val bits: Double = 0.0,
    val bmd: Double = 0.0,
    val bnb: Double = 0.0,
    val brl: Double = 0.0,
    val btc: Double = 0.0,
    val cad: Double = 0.0,
    val chf: Double = 0.0,
    val clp: Double = 0.0,
    val cny: Double = 0.0,
    val czk: Double = 0.0,
    val dkk: Double = 0.0,
    val dot: Double = 0.0,
    val eos: Double = 0.0,
    val eth: Double = 0.0,
    val eur: Double = 0.0,
    val gbp: Double = 0.0,
    val gel: Double = 0.0,
    val hkd: Double = 0.0,
    val huf: Double = 0.0,
    val idr: Double = 0.0,
    val ils: Double = 0.0,
    val inr: Double = 0.0,
    val jpy: Double = 0.0,
    val krw: Double = 0.0,
    val kwd: Double = 0.0,
    val link: Double = 0.0,
    val lkr: Double = 0.0,
    val ltc: Double = 0.0,
    val mmk: Double = 0.0,
    val mxn: Double = 0.0,
    val myr: Double = 0.0,
    val ngn: Double = 0.0,
    val nok: Double = 0.0,
    val nzd: Double = 0.0,
    val php: Double = 0.0,
    val pkr: Double = 0.0,
    val pln: Double = 0.0,
    val rub: Double = 0.0,
    val sar: Double = 0.0,
    val sats: Double = 0.0,
    val sek: Double = 0.0,
    val sgd: Double = 0.0,
    val thb: Double = 0.0,
    val twd: Double = 0.0,
    val uah: Double = 0.0,
    val usd: Double = 0.0,
    val vef: Double = 0.0,
    val vnd: Double = 0.0,
    val xag: Double = 0.0,
    val xau: Double = 0.0,
    val xdr: Double = 0.0,
    val xlm: Double = 0.0,
    val xrp: Double = 0.0,
    val yfi: Double = 0.0,
    val zar: Double = 0.0
)
