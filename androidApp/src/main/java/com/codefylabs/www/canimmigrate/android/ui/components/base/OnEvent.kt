package com.codefylabs.www.canimmigrate.android.ui.components.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.codefylabs.www.canimmigrate.core.util.Event
import kotlinx.coroutines.flow.Flow


@Composable
fun <E : Event> OnEvent(event: Flow<E>, onEvent: (E) -> Unit) {
    LaunchedEffect(Unit) {
        event.collect(onEvent)
    }
}