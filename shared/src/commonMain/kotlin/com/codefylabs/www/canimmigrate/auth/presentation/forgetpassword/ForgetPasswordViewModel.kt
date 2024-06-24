package com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword

import com.codefylabs.www.canimmigrate.auth.domain.usescases.ForgotPasswordUseCase
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.core.util.Validator

class ForgetPasswordViewModel(
    private val forgetPasswordUseCase: ForgotPasswordUseCase
) : StateViewModel<ForgetPasswordEvent, ForgetPasswordState>(ForgetPasswordState.init()) {

    fun onEmailChanged(email: String) {
        updateState(state.value.copy(email = email))
    }

    suspend fun sendResetCodeOnEmail() {

        if (!Validator.isEmailValid(state.value.email)) {
            sendEventSync(ForgetPasswordEvent.Error("Please enter valid email address."))
            return
        }

        updateState(state.value.copy(isLoading = true))

        when (val result =
            forgetPasswordUseCase(state.value.email)) {
            is Either.Error -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(ForgetPasswordEvent.Error(result.message))
            }

            is Either.Success -> {
                updateState(state.value.copy(isLoading = false))
                sendEvent(ForgetPasswordEvent.Success(result.data))
            }
        }
    }

}
