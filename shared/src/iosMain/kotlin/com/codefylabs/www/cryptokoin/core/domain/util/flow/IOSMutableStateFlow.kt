package com.codefylabs.www.cryptokoin.core.domain.util.flow

import kotlinx.coroutines.flow.MutableStateFlow


class IOSMutableStateFlow<T>(
    initialValue: T
): CommonMutableStateFlow<T>(MutableStateFlow(initialValue))
