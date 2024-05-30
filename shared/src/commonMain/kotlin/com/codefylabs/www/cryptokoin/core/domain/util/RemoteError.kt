package com.codefylabs.www.cryptokoin.core.domain.util

enum class RemoteError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class RemoteException(val error: RemoteError) : Exception(
    "Oops!! Something went wrong.\n$error"
)