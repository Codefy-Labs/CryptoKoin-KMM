package com.codefylabs.www.cryptokoin.core.domain.util.flow

import kotlinx.coroutines.flow.Flow

actual class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow