package com.codefylabs.www.canimmigrate.auth.presentation

import com.codefylabs.www.canimmigrate.auth.domain.models.GoogleUser
import com.codefylabs.www.canimmigrate.auth.domain.usescases.LoginUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SessionUseCase
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginSharedVM(
    private val loginUseCase: LoginUseCase,
    private val sessionUseCase: SessionUseCase,
) : StateViewModel<LoginEvent, LoginViewState>(LoginViewState.initial()) {

    init {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            sessionUseCase.getUserEmail()?.let {
                updateState(newState = state.value.copy(emailId = it))
            }
        }
    }

    fun loginWithGoogle(googleUser: GoogleUser){
      coroutine.launch {
           Napier.i("GoogleUser -> ${googleUser}")
      }
    }

    fun onChangeEmailId(email: String) =
        updateState(state.value.copy(emailId = email))

    fun onChangePassword(password: String) =
        updateState(state.value.copy(password = password))

    fun togglePasswordVisibility() =
        updateState(state.value.copy(passwordVisibility = !state.value.passwordVisibility))

    suspend fun login() {
        updateState(state.value.copy(isLoading = true))

        when (val result = loginUseCase(state.value.emailId, state.value.password)) {

            is Either.Error -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(LoginEvent.Error(result.message))
            }

            is Either.Success -> {
//                AnalyticsManager.setUserId(id = state.value.emailId)
//                AnalyticsManager.logEvent(EventName.UserAuthenticationSuccessful)
//
//                AnalyticsManager.logEvent(EventName.HeartbeatStarted)

                updateState(state.value.copy(isLoading = false))

                sendEvent(LoginEvent.NavigateToDashboard("Logged In Successfully"))

            }

        }
    }


}

data class LoginViewState(
    val isLoading: Boolean = false,
    val emailId: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
) : State {
    companion object {
        fun initial() = LoginViewState()
    }
}

sealed class LoginEvent : Event {
    data class NavigateToDashboard(val message: String) : LoginEvent()
    data class Error(val error: String) : LoginEvent()

}
