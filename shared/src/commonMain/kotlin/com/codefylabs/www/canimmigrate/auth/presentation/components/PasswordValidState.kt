package com.codefylabs.www.canimmigrate.auth.presentation.components

data class PasswordValidState(
    var isValidLength: Boolean = false,
    var hasLowerCase: Boolean = false,
    var hasUpperCase: Boolean = false,
    var hasDigit: Boolean = false,
    var hasSpecialChar: Boolean = false
) {

    fun isValid(): Boolean {
        return isValidLength && hasLowerCase && hasUpperCase && hasDigit && hasSpecialChar
    }

    companion object {
        fun validatePassword(password: String) = PasswordValidState(
            isValidLength = password.length >= 8,
            hasLowerCase = password.any { it.isLowerCase() },
            hasUpperCase = password.any { it.isUpperCase() },
            hasDigit = password.any { it.isDigit() },
            hasSpecialChar = password.any { it.isLetterOrDigit().not() }
        )

    }

}

