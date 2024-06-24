package com.codefylabs.www.canimmigrate

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


actual val platformModule: Module = module {

    single<HttpClient> {
        HttpClient(OkHttp) {

            engine {
                config {
                    retryOnConnectionFailure(true)
                    connectTimeout(10, TimeUnit.SECONDS)
                }
            }
        }
    }

}
