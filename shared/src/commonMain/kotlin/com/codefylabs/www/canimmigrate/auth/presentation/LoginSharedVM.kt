package com.codefylabs.www.canimmigrate.auth.presentation

import com.codefylabs.www.canimmigrate.auth.domain.models.GoogleUser
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignInUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SessionUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignInWithGoogleUseCase
import com.codefylabs.www.canimmigrate.auth.presentation.signup.SignupViewEvent
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginSharedVM(
    private val signInUseCase: SignInUseCase,
    private val sessionUseCase: SessionUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    ) : StateViewModel<LoginEvent, LoginViewState>(LoginViewState.initial()) {

    init {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            sessionUseCase.getUserEmail()?.let {
                updateState(newState = state.value.copy(emailId = it))
            }
        }
    }

    fun onChangeEmailId(email: String) =
        updateState(state.value.copy(emailId = email))

    fun onChangePassword(password: String) =
        updateState(state.value.copy(password = password))

    fun togglePasswordVisibility() =
        updateState(state.value.copy(passwordVisibility = !state.value.passwordVisibility))

    fun login() {
        coroutine.launch {
            validateCredentials(
                emailId = state.value.emailId,
                password = state.value.password
            )?.let {
                sendEventSync(LoginEvent.Error(it))
                return@launch
            }

            updateState(state.value.copy(isLoading = true))
            when (val result = signInUseCase(state.value.emailId, state.value.password)) {
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

    private fun validateCredentials(emailId: String, password: String): String? {
        // Regular expression to check valid email id.
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()

        // Check if email is not empty and matches the regex pattern
        if (emailId.isEmpty()) {
            return "Email ID cannot be empty"
        } else if (!emailId.matches(emailRegex)) {
            return "Invalid email ID"
        }

        // Check if password is not empty and has at least 8 characters
        if (password.isEmpty()) {
            return "Password cannot be empty"
        }

        return null
    }

    fun signInWithGoogle(idToken: String) {
        coroutine.launch {
            updateState(state.value.copy(isGoogleSigning = true))
            when (val result = signInWithGoogleUseCase(idToken)) {
                is Either.Error -> {
                    updateState(state.value.copy(isGoogleSigning = false))
                    sendEvent(LoginEvent.Error(result.message))
                }

                is Either.Success -> {
                    updateState(state.value.copy(isGoogleSigning = false))
                    sendEvent(LoginEvent.NavigateToDashboard("Welcome, ${result.data.name ?: ""}"))
                }
            }
        }
    }

}

data class LoginViewState(
    val isLoading: Boolean = false,
    val isGoogleSigning: Boolean = false,
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
