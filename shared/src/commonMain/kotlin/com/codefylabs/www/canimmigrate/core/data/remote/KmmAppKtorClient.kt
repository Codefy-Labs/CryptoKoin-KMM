package com.codefylabs.www.canimmigrate.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class KmmAppKtorClient constructor(
    val client: HttpClient,
    private val json: Json,
) {
    fun instance(): HttpClient {
        return client.config {
            install(ContentNegotiation) {
                json(json)
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}