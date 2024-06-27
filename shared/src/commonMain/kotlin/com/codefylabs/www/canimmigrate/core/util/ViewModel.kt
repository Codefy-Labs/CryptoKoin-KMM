package com.codefylabs.www.canimmigrate.core.util

import com.codefylabs.www.canimmigrate.core.domain.util.flow.toCommonFlow
import com.codefylabs.www.canimmigrate.core.domain.util.flow.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

expect abstract class ViewModel() {

    val coroutine: CoroutineScope

    protected open fun onCleared()
}


interface Event {

}

interface State {

}

abstract class StateViewModel<E : Event, S : State>(initialState: S) : ViewModel() {
    private val _event = Channel<E>()
    val event =
        _event.receiveAsFlow().shareIn(CoroutineScope(Dispatchers.Main), SharingStarted.Lazily)
            .toCommonFlow()

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow().toCommonStateFlow()

    protected suspend fun sendEvent(event: E) = _event.send(event)

    @OptIn(DelicateCoroutinesApi::class)
    protected fun sendEventSync(event: E) = GlobalScope.launch { _event.send(event) }

    protected fun updateState(newState: S) {
       CoroutineScope(Dispatchers.Main.immediate).launch {
           _state.value = newState
       }
    }

    override fun onCleared() {
        _event.close()
        super.onCleared()

    }
}