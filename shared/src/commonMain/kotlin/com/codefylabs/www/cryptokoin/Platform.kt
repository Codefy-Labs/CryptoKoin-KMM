package com.codefylabs.www.cryptokoin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform