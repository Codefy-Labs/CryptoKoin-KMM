package com.codefylabs.www.canimmigrate.auth.presentation.resetpassword

import com.codefylabs.www.canimmigrate.auth.domain.usescases.ResetPasswordUseCase
import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.auth.presentation.resetpassword.ResetPasswordEvent
import com.codefylabs.www.canimmigrate.auth.presentation.resetpassword.ResetPasswordState
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.core.util.Validator


class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : StateViewModel<ResetPasswordEvent, ResetPasswordState>(ResetPasswordState.init()) {


    fun onEmailChanged(email: String) {
        updateState(state.value.copy(email = email))
    }

    fun onVerificationCodeChanged(code: String) {
        updateState(state.value.copy(verificationCode = code))
    }

    fun onPasswordChanged(password: String) {
        updateState(
            state.value.copy(
                newPassword = password,
                passwordValidState = PasswordValidState.validatePassword(password)
            )
        )
    }


    suspend fun resetPassword() {

        if (!Validator.isEmailValid(state.value.email)) {
            sendEventSync(ResetPasswordEvent.Error("Please enter valid email address."))
            return
        }

        if (state.value.newPassword.trim().isEmpty()) {
            sendEventSync(ResetPasswordEvent.Error("Please enter your new password."))
            return
        }

        if (!state.value.passwordValidState.isValid()) {
            sendEventSync(ResetPasswordEvent.Error("Please enter a password that includes all cases."))
            return
        }

        updateState(state.value.copy(isLoading = true))

        when (val result =
            resetPasswordUseCase(
                state.value.email,
                state.value.newPassword,
                state.value.verificationCode
            )) {
            is Either.Error -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(ResetPasswordEvent.Error(result.message))
            }

            is Either.Success -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(ResetPasswordEvent.Success(result.data))
            }
        }
    }


}
