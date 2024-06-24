package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel


class ProfileSharedVM(
) : StateViewModel<ProfileEvent, ProfileViewState>(ProfileViewState.initial()) {




}

sealed class ProfileEvent : Event {
    data class Success(val message: String) : ProfileEvent()
    data class Error(val error: String) : ProfileEvent()
}

data class ProfileViewState(
    val isLoading: Boolean = false,
) : State {
    companion object {
        fun initial() = ProfileViewState()
    }
}


