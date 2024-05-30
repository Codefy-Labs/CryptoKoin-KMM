package com.codefylabs.www.spider.core.data.remote

import com.codefylabs.www.spider.core.data.models.ApiWrapper
import com.codefylabs.www.spider.core.util.NetworkResult
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HeadersBuilder
import io.ktor.http.contentType
import kotlinx.serialization.json.Json


suspend inline fun KmmAppKtorClient.submitFormWithData(
    url: String,
    noinline headers: HeadersBuilder.() -> Unit = {},
    noinline formData: FormBuilder.() -> Unit
) = runCatching {
    instance().post(url) {
        headers(headers)
        setBody(MultiPartFormDataContent(formData(formData)))
    }
}

suspend inline fun <reified T> KmmAppKtorClient.submitWithBody(
    url: String, noinline headers: HeadersBuilder.() -> Unit = {}, body: T
) = runCatching {
    instance().post(url) {
        contentType(ContentType.Application.Json)
        headers(headers)
        setBody(body)
    }
}

suspend inline fun <reified T> KmmAppKtorClient.submitWithBodyAndAuthHeader(
    url: String, noinline headers: HeadersBuilder.() -> Unit, body: T
) = runCatching {
    client.post(url) {
        contentType(ContentType.Application.Json)
        headers(headers)
        setBody(body)
    }
}

suspend inline fun KmmAppKtorClient.get(
    url: String, noinline headers: HeadersBuilder.() -> Unit = {}, vararg params: Pair<String, Any>
) = runCatching {
    instance().get(url) {
        headers(headers)
        params.forEach {
            parameter(it.first, it.second)
        }
    }
}

suspend inline fun KmmAppKtorClient.getWithAuthHeaders(
    url: String, noinline authHeaders: HeadersBuilder.() -> Unit = {}
) = runCatching {
    client.get(url) {
        headers(authHeaders)
    }
}

suspend inline fun KmmAppKtorClient.put(
    url: String, noinline headers: HeadersBuilder.() -> Unit = {}, vararg params: Pair<String, Any>

) = runCatching {
    instance().put(url) {
        headers(headers)
        params.forEach {
            parameter(it.first, it.second)
        }
    }
}

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}

suspend fun Result<HttpResponse>.toResult(): NetworkResult {
    return when {
        isSuccess -> getOrThrow().run {

            when (this.status.value) {
                200, 201, 202, 204, 205 -> {
                    NetworkResult.Success(body())
                }

                400 -> {
                    val errorBody: ApiWrapper<String?> = json.decodeFromString(bodyAsText())
                    val errorMessage = errorBody.response ?: errorBody.message
                    NetworkResult.Error(
                        NetworkException(
                            errorMessage ?: "Something went wrong! Please try again later."
                        )
                    )
                }

                401 -> {
                    val errorBody: ApiWrapper<String?> = json.decodeFromString(bodyAsText())
                    val errorMessage = errorBody.response ?: errorBody.message
                    NetworkResult.Error(
                        NetworkException(
                            errorMessage ?: "Authorization Failed! Try Logging In again."
                        )
                    )
                }

                in listOf(500, 503) -> {
                    val errorBody: ApiWrapper<String?> = json.decodeFromString(bodyAsText())
                    val errorMessage = errorBody.response ?: errorBody.message
                    NetworkResult.Error(
                        NetworkException(
                            errorMessage ?: "Server Disruption! We are on fixing it."
                        )
                    )
                }

                504 -> {
                    val errorBody: ApiWrapper<String?> = json.decodeFromString(bodyAsText())
                    val errorMessage = errorBody.response ?: errorBody.message
                    NetworkResult.Error(
                        NetworkException(
                            errorMessage ?: "Too much load at this time, try again later!"
                        )
                    )
                }

                else -> {
                    val errorBody: ApiWrapper<String?> = json.decodeFromString(bodyAsText())
                    val errorMessage = errorBody.response ?: errorBody.message
                    NetworkResult.Error(
                        NetworkException(
                            errorMessage ?: "Something went wrong! Please try again later."
                        )
                    )
                }
            }
        }

        else -> {
            Napier.e("HttpFailure -> ${this.exceptionOrNull()?.message}")
            Napier.e("HttpFailure -> ${this.exceptionOrNull()?.cause?.message}")
            this.exceptionOrNull()?.printStackTrace()
            NetworkResult.Error(KtorException("Something went wrong! Please try again later."))
        }
    }

}


class NetworkException(message: String) : Exception(message)
class KtorException(message: String) : Exception(message)

