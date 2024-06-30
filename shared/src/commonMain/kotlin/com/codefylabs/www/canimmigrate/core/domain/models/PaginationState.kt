package com.codefylabs.www.canimmigrate.core.domain.models




data class PagingState<T>(
    val isRefreshing: Boolean = false,
    val page: Int = 1,
    val data: List<T> = emptyList(),
    val isReachedEnd: Boolean = false,
    val pageCount: Int = 20,
    val error : String? = null
)