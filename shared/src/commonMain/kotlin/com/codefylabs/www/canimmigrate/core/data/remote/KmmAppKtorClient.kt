package com.codefylabs.www.canimmigrate.core.data.remote

import com.codefylabs.www.canimmigrate.BaseConfig
import com.codefylabs.www.canimmigrate.auth.data.local.AuthPersistence
import com.codefylabs.www.canimmigrate.auth.domain.entities.Session
import com.codefylabs.www.canimmigrate.auth.domain.entities.toRealmObject
import com.codefylabs.www.canimmigrate.auth.domain.entities.toSession
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.Parameters
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class KmmAppKtorClient constructor(
    val client: HttpClient,
    private val json: Json,
    private val authPrefs: AuthPersistence,
) {
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    suspend fun instance(): HttpClient {
        var session: Session? = Session()

        val job = scope.launch {
            session = authPrefs.getSession()?.toSession()
        }

        job.join()

        return client.config {
            install(Auth) {

                bearer {
                    session.takeIf { !it?.accessToken.isNullOrBlank() && !it?.refreshToken.isNullOrBlank() }
                        ?.let { session ->
                            if (!session.accessToken.isNullOrBlank() && !session.refreshToken.isNullOrBlank()) {
                                Napier.e("SessionAuthorization is injected in headers successfully. ")
                                loadTokens {
                                    BearerTokens(
                                        session.accessToken.toString(),
                                        session.refreshToken.toString()
                                    )
                                }

                                refreshTokens {
                                    val refreshTokenInfo: Session = client.submitForm(
                                        url = "${BaseConfig.BASE_URL}/auth/refresh-token",
                                    ) {
                                        this.headers {
                                            this.append("Authorization", "Bearer ${session.refreshToken}")
                                        }
                                        markAsRefreshTokenRequest()
                                    }.body()

                                    authPrefs.writeSession(refreshTokenInfo.toRealmObject())

                                    BearerTokens(
                                        accessToken = refreshTokenInfo.accessToken.toString(),
                                        refreshToken = refreshTokenInfo.refreshToken.toString()
                                    )
                                }
                            } else Napier.e("SessionAuthorization is not injected in headers. ElseCase ")
                        } ?: Napier.e("SessionAuthorization is not injected in headers. ?:Case")

                }
            }
        }
    }
}