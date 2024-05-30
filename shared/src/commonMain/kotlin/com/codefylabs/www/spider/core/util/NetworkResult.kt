package com.codefylabs.www.spider.core.util

sealed interface NetworkResult {
    data class Success(val data: ByteArray) : NetworkResult {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || this::class != other::class) return false

            other as Success
            return data.contentEquals(other.data)
        }

        override fun hashCode(): Int {
            return data.contentHashCode()
        }
    }

    data class Error(val error: Exception) : NetworkResult
}