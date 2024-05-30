package com.codefylabs.www.cryptokoin.settings.domain.models

import com.codefylabs.www.cryptokoin.settings.data.local.entity.PortfolioObject

data class Portfolio(
    val coinId: String,
    val amount: Double
)

fun Portfolio.toRealmObject() = PortfolioObject().also {
    it.coinId = this.coinId
    it.amount = this.amount
}

fun PortfolioObject.toPortfolio() = Portfolio(coinId = coinId, amount = amount)