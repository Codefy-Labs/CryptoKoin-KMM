package com.codefylabs.www.spider.android.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.codefylabs.www.spider.core.util.Event
import com.codefylabs.www.spider.core.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun <E : Event> OnEvent(event: Flow<E>, onEvent: (E) -> Unit) {
    LaunchedEffect(Unit) {
        event.collect(onEvent)
    }
}

@Composable
fun <S : State> OnState(stateFlow: StateFlow<S?>, onState: (S) -> Unit) {
    val state = stateFlow.collectAsState()
    LaunchedEffect(state.value) {
        state.value?.let(onState)
    }
}