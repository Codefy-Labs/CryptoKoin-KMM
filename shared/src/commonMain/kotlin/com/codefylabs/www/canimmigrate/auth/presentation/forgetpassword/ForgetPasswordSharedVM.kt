package com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword

import com.codefylabs.www.canimmigrate.auth.domain.usescases.ForgotPasswordUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.ResetPasswordUseCase
import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.core.util.Validator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgetPasswordSharedVM(
    private val forgetPasswordUseCase: ForgotPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : StateViewModel<ForgetPasswordEvent, ForgetPasswordState>(ForgetPasswordState.init()) {

    fun onEmailChanged(email: String) {
        updateState(state.value.copy(email = email))
    }

    fun onOtpChanged(otp: String) {
        updateState(state.value.copy(otp = otp))
    }

    fun editEmail() {
        updateState(state.value.copy(isOtpSent = false, newPassword = "", otp = ""))
    }

    fun onPasswordChanged(password: String) {
        updateState(
            state.value.copy(
                newPassword = password,
                passwordValidState = PasswordValidState.validatePassword(password)
            )
        )
    }

    fun handleSubmit() {
        if (state.value.isOtpSent) {
            resetPassword()
        } else {
            sendResetCodeOnEmail()
        }
    }

    fun togglePasswordVisibility() {
        updateState(state.value.copy(passwordVisibility = !state.value.passwordVisibility))
    }

    private fun sendResetCodeOnEmail() {

        coroutine.launch {
            if (!Validator.isEmailValid(state.value.email)) {
                sendEventSync(ForgetPasswordEvent.Error("Please enter valid email address."))
                return@launch
            }

            updateState(state.value.copy(isLoading = true, isOtpSent = false))
            when (val result = forgetPasswordUseCase(state.value.email)) {
                is Either.Error -> {
                    updateState(state.value.copy(isLoading = false, isOtpSent = false))
                    sendEventSync(ForgetPasswordEvent.Error(result.message))
                }

                is Either.Success -> {
                    updateState(state.value.copy(isLoading = false, isOtpSent = true))
                    sendEventSync(ForgetPasswordEvent.OtpSent(result.data))
                }
            }
        }
    }

    private fun resetPassword() {
        coroutine.launch {
            if (state.value.newPassword.trim().isEmpty()) {
                sendEventSync(ForgetPasswordEvent.Error("Please enter your new password."))
                return@launch
            }

            if (!state.value.passwordValidState.isValid()) {
                sendEventSync(ForgetPasswordEvent.Error("Please enter a password that includes all cases."))
                return@launch
            }

            updateState(state.value.copy(isLoading = true))

            when (val result =
                resetPasswordUseCase(
                    state.value.email,
                    state.value.newPassword,
                    state.value.otp
                )) {
                is Either.Error -> {
                    updateState(state.value.copy(isLoading = false))
                    sendEvent(ForgetPasswordEvent.Error(result.message))
                }

                is Either.Success -> {
                    updateState(state.value.copy(isLoading = false))
                    sendEvent(ForgetPasswordEvent.PasswordResetSuccess(result.data))
                }
            }
        }

    }

}
