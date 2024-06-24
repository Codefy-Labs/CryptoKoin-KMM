package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel


class ProcessSharedVM(
) : StateViewModel<ProcessEvent, ProcessViewState>(ProcessViewState.initial()) {




}

sealed class ProcessEvent : Event {
    data class Success(val message: String) : ProcessEvent()
    data class Error(val error: String) : ProcessEvent()
}

data class ProcessViewState(
    val isLoading: Boolean = false,
) : State {
    companion object {
        fun initial() = ProcessViewState()
    }
}


