package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.auth.domain.entities.Session
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SessionUseCase
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ProfileSharedVM(
    private val sessionUseCase: SessionUseCase,
) : StateViewModel<ProfileEvent, ProfileViewState>(ProfileViewState.initial()) {

    init {
        coroutine.launch {
            sessionUseCase.getSessionFlow().collectLatest {
                updateState(state.value.copy(session = it))
            }
        }
    }

    fun logout(){
      coroutine.launch {
          sessionUseCase.logout()
      }
    }

}

sealed class ProfileEvent : Event {
    data class Success(val message: String) : ProfileEvent()
    data class Error(val error: String) : ProfileEvent()
}

data class ProfileViewState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val session: Session? = null
) : State {
    companion object {
        fun initial() = ProfileViewState()
    }
}


