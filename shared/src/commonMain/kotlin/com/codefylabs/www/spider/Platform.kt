package com.codefylabs.www.spider

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform