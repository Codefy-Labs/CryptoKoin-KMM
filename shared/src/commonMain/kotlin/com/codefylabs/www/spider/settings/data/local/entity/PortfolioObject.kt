package com.codefylabs.www.spider.settings.data.local.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PortfolioObject: RealmObject {
    @PrimaryKey
    var coinId: String = ""
    var amount: Double = 0.0
}