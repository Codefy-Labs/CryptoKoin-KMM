package com.codefylabs.www.canimmigrate.auth.presentation.updatepassword

import com.codefylabs.www.canimmigrate.auth.domain.usescases.UpdatePasswordUseCase
import com.codefylabs.www.canimmigrate.auth.presentation.components.PasswordValidState
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.StateViewModel


class UpdatePasswordViewModel(
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : StateViewModel<UpdatePasswordEvent, UpdatePasswordState>(UpdatePasswordState.initial()) {

    fun onNewPasswordChange(password: String) =
        updateState(
            state.value.copy(
                newPassword = password,
                passwordValidState = PasswordValidState.validatePassword(password)
            )
        )

    fun onOldPasswordChange(password: String) =
        updateState(state.value.copy(oldPassword = password))

    fun onConfirmNewPasswordChanged(password: String) =
        updateState(state.value.copy(confirmNewPassword = password))


    suspend fun updatePassword() {
        if (state.value.newPassword.trim().isEmpty()) {
            sendEvent(UpdatePasswordEvent.Error("Please enter your new password."))
            return
        }

        if (!state.value.passwordValidState.isValid()) {
            sendEventSync(UpdatePasswordEvent.Error("Please enter a password that includes all cases."))
            return
        }

        if(state.value.confirmNewPassword != state.value.newPassword){
            sendEventSync(UpdatePasswordEvent.Error("New Password not matched"))
            return
        }

        updateState(state.value.copy(isLoading = true))

        when (val result =
            updatePasswordUseCase(state.value.oldPassword, state.value.newPassword)) {

            is Either.Error -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(UpdatePasswordEvent.Error(result.message))
            }

            is Either.Success -> {
                sendEvent(UpdatePasswordEvent.Success(result.data))
            }

        }
    }


}