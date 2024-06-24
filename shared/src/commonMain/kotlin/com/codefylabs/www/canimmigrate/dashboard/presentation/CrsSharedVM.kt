package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel


class CrsSharedVM(
) : StateViewModel<CrsEvent, CrsViewState>(CrsViewState.initial()) {




}

sealed class CrsEvent : Event {
    data class Success(val message: String) : CrsEvent()
    data class Error(val error: String) : CrsEvent()
}

data class CrsViewState(
    val isLoading: Boolean = false,
) : State {
    companion object {
        fun initial() = CrsViewState()
    }
}


