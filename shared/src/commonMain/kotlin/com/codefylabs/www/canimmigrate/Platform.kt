package com.codefylabs.www.canimmigrate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform