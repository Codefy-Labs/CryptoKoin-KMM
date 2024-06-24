package com.codefylabs.www.canimmigrate.core.domain.util.flow

import kotlinx.coroutines.flow.MutableStateFlow


class IOSMutableStateFlow<T>(
    initialValue: T
): CommonMutableStateFlow<T>(MutableStateFlow(initialValue))
