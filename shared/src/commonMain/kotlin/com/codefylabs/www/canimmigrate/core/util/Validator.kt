package com.codefylabs.www.canimmigrate.core.util

object Validator {

    private val emailAddressRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isEmailValid(email: String) = email.matches(emailAddressRegex)

    fun isValidPhoneNumber(number: String): Boolean {
        val regEx = """^\+(?:[0-9]?){6,14}[0-9]$""".toRegex()
        return regEx.matches(number)
    }

}