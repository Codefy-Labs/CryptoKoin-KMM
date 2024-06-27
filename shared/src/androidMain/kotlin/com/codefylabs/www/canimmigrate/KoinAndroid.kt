package com.codefylabs.www.canimmigrate

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


actual val platformModule: Module = module {

    single<HttpClient> {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(get<Json>())
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            engine {
                config {
                    retryOnConnectionFailure(true)
                    connectTimeout(10, TimeUnit.SECONDS)
                }
            }
        }
    }
}



