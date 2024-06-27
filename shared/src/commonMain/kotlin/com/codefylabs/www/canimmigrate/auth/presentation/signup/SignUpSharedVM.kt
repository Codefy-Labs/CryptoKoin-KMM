package com.codefylabs.www.canimmigrate.auth.presentation.signup

import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignInWithGoogleUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SignupUseCase
import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.core.util.Validator
import kotlinx.coroutines.launch

class SignUpSharedVM(
    private val signupUseCase: SignupUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : StateViewModel<SignupViewEvent, SignupViewState>(SignupViewState()) {

    fun onPasswordChanged(password: String) {
        updateState(
            state.value.copy(
                password = password,
                passwordValidState = PasswordValidState.validatePassword(password)
            )
        )
    }

    fun onConfirmPasswordChange(password: String) {
        updateState(
            state.value.copy(
                confirmPassword = password,
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

        if (state.value.password != state.value.confirmPassword) {
            sendEventSync(SignupViewEvent.Error("Passwords do not match."))
            return false
        }

        return true
    }

    fun signUp() {
        coroutine.launch {
            if (!isAllFieldValid())
                return@launch

            updateState(state.value.copy(isLoading = true))

            when (val result =
                signupUseCase(state.value.name, state.value.email, state.value.password)) {
                is Either.Error -> {
                    updateState(state.value.copy(isLoading = false))
                    sendEvent(SignupViewEvent.Error(result.message))
                }

                is Either.Success -> {
                    updateState(state.value.copy(isLoading = false))
                    sendEvent(SignupViewEvent.VerificationEmailSent(result.data))
                }
            }
        }
    }

    fun signUpWithGoogle(idToken: String) {
        coroutine.launch {
            updateState(state.value.copy(isGoogleSigning = true))
            when (val result = signInWithGoogleUseCase(idToken)) {
                is Either.Error -> {
                    updateState(state.value.copy(isGoogleSigning = false))
                    sendEvent(SignupViewEvent.Error(result.message))
                }

                is Either.Success -> {
                    updateState(state.value.copy(isGoogleSigning = false))
                    sendEvent(SignupViewEvent.GoogleSignUpSuccessful("Welcome, ${result.data.name ?: ""}"))
                }
            }
        }
    }

}

data class SignupViewState(
    val isLoading: Boolean = false,
    val isGoogleSigning: Boolean = false,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val passwordValidState: PasswordValidState = PasswordValidState(),
) : State {
    companion object {
        fun emptyState() = SignupViewState()
    }

}

sealed class SignupViewEvent : Event {
    data class GoogleSignUpSuccessful(val message: String) : SignupViewEvent()
    data class VerificationEmailSent(val message: String) : SignupViewEvent()
    data class Error(val error: String) : SignupViewEvent()
}