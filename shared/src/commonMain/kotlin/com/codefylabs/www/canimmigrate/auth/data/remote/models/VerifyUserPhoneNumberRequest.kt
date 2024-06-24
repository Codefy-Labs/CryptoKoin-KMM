package com.codefylabs.www.canimmigrate.auth.data.remote.models

import kotlinx.serialization.Serializable


@Serializable
data class VerifyUserPhoneNumberRequest(val phoneNumber : String, val otp : String)