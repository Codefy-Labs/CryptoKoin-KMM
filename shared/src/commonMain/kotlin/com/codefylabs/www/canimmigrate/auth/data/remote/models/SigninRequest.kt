package com.codefylabs.www.canimmigrate.auth.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SigninRequest(
   @SerialName("email") val email : String,
   @SerialName("password")  val password : String
)