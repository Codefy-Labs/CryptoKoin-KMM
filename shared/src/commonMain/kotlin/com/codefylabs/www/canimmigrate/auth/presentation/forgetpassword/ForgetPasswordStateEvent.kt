package com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State

data class ForgetPasswordState(
    val isLoading: Boolean = false,
    val email: String = "",
) : State {
    companion object {
        fun init() = ForgetPasswordState()
    }

}

sealed class ForgetPasswordEvent : Event {
    data class Success(val message: String) : ForgetPasswordEvent()
    data class Error(val error: String) : ForgetPasswordEvent()
}