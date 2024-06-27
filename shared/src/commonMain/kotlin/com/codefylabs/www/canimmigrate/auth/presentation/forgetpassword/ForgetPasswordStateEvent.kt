package com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword

import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State

data class ForgetPasswordState(
    val isLoading: Boolean = false,
    val isOtpSent: Boolean = false,
    val email: String = "",
    val otp: String = "",
    val newPassword: String = "",
    val passwordValidState: PasswordValidState = PasswordValidState(),
    val passwordVisibility : Boolean = false,
) : State {
    companion object {
        fun init() = ForgetPasswordState()
    }

}

sealed class ForgetPasswordEvent : Event {
    data class OtpSent(val message: String) : ForgetPasswordEvent()
    data class PasswordResetSuccess(val message: String) : ForgetPasswordEvent()
    data class Error(val error: String) : ForgetPasswordEvent()
}