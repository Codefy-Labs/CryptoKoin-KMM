package com.codefylabs.www.canimmigrate.auth.presentation.signup

import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignupUseCase
import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.core.util.Validator

class SignUpSharedVM(
    private val signupUseCase: SignupUseCase
) : StateViewModel<SignupViewEvent, SignupViewState>(SignupViewState()) {

    fun onPasswordChanged(password: String) {
        updateState(
            state.value.copy(
                password = password,
                passwordValidState = PasswordValidState.validatePassword(password)
            )
        )
    }

    fun onNameChanged(name: String) {
        updateState(state.value.copy(name = name))
    }

    fun onEmailChanged(email: String) {
        updateState(state.value.copy(email = email))
    }


    private fun isAllFieldValid(): Boolean {
        val trimmedName = state.value.name.trim()
        val trimmedEmail = state.value.email.trim()
        val trimmedPassword = state.value.password.trim()

        if (trimmedName.isEmpty()) {
            sendEventSync(SignupViewEvent.Error("Please enter your full name."))
            return false
        }

        if (trimmedEmail.isEmpty()) {
            sendEventSync(SignupViewEvent.Error("Please enter your email address."))
            return false
        }

        if (!Validator.isEmailValid(trimmedEmail)) {
            sendEventSync(SignupViewEvent.Error("Please enter valid email address."))
            return false
        }

        if (trimmedPassword.isEmpty()) {
            sendEventSync(SignupViewEvent.Error("Please enter your password."))
            return false
        }

        if (!state.value.passwordValidState.isValid()) {
            sendEventSync(SignupViewEvent.Error("Please enter a password that includes all cases."))
            return false
        }

        return true
    }

    suspend fun signUp() {

        if (!isAllFieldValid())
            return

        updateState(state.value.copy(isLoading = true))

        when (val result =
            signupUseCase(state.value.name, state.value.email, state.value.password)) {
            is Either.Error -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(SignupViewEvent.Error(result.message))
            }

            is Either.Success -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(SignupViewEvent.Success(result.data))
            }
        }
    }

}

data class SignupViewState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val passwordValidState: PasswordValidState = PasswordValidState()
) : State {
    companion object {
        fun emptyState() = SignupViewState()
    }

}

sealed class SignupViewEvent : Event {
    data class Success(val message: String) : SignupViewEvent()
    data class Error(val error: String) : SignupViewEvent()
}