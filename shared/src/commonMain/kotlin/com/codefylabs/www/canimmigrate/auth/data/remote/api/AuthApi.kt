package com.codefylabs.www.canimmigrate.auth.data.remote.api

import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey
import com.codefylabs.www.canimmigrate.core.util.NetworkResult


interface AuthAPI {
    suspend fun signIn(
        email: String,
        password: String
    ): NetworkResult

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): NetworkResult

    suspend fun forgetPassword(
        email: String
    ): NetworkResult

    suspend fun resetPassword(
        email: String,
        verificationCode: String,
        newPassword: String
    ): NetworkResult

    suspend fun signInWithGoogle(
        token : String
    ) : NetworkResult

    suspend fun removeAccount(): NetworkResult
    suspend fun sendOtpOnUserPhoneNumber(phoneNumber: String): NetworkResult
    suspend fun resendOtpOnUserPhoneNumber(phoneNumber: String): NetworkResult
    suspend fun verifyUserPhoneNumber(phoneNumber: String, otp: String): NetworkResult

    suspend fun updatePassword(oldPassword: String, newPassword: String) : NetworkResult

    suspend fun updateOnboardingSurvey(list: List<Survey>) : NetworkResult

}