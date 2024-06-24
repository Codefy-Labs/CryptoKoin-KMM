package com.codefylabs.www.canimmigrate.auth.presentation.resetpassword

import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State

data class ResetPasswordState(
    val isLoading: Boolean = false,
    val email: String = "",
    val newPassword: String = "",
    val verificationCode: String = "",
    val passwordValidState: PasswordValidState = PasswordValidState()
) : State {
    companion object {
        fun init() = ResetPasswordState()
    }

}

sealed class ResetPasswordEvent : Event {
    data class Success(val message: String) : ResetPasswordEvent()
    data class Error(val error: String) : ResetPasswordEvent()
}