package com.codefylabs.www.canimmigrate.auth.presentation.updatepassword

import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State


data class UpdatePasswordState(
    val isLoading: Boolean = false,
    val newPassword: String = "",
    val confirmNewPassword: String = "",
    val oldPassword: String = "",
    val oldPasswordVisibility: Boolean = false,
    val newPasswordVisibility: Boolean = false,
    val passwordValidState: PasswordValidState = PasswordValidState()
) : State {
    companion object {
        fun initial() = UpdatePasswordState()
    }
}

sealed class UpdatePasswordEvent : Event {
    data class Error(val error: String) : UpdatePasswordEvent()
    data class Success(val message: String) : UpdatePasswordEvent()

}