package com.codefylabs.www.canimmigrate.auth.domain.repo

import com.codefylabs.www.canimmigrate.auth.domain.entities.LocalData
import com.codefylabs.www.canimmigrate.auth.domain.entities.Session
import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey
import com.codefylabs.www.canimmigrate.auth.domain.entities.UserInfo
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun signIn(email: String, password: String): UserInfo
    suspend fun signInWithGoogle(idToken : String): UserInfo
    suspend fun signUp(name: String, email: String, password: String): String
    suspend fun logout()
    suspend fun forgetPassword(email: String): String
    suspend fun resetPassword(email: String, confirmationCode: String, newPassword: String): String
    suspend fun resendCode(email: String)
    suspend fun getUserSession(): Session?
    suspend fun getSessionFlow(): Flow<Session?>
    suspend fun deleteAccount(): Unit
    fun getLocalData(): LocalData
    suspend fun saveOnboardingSurvey(
        survey: List<Survey>,
    ):  String

    suspend fun sendOtpOnUserPhoneNumber(phoneNumber: String): String
    suspend fun resendOtpOnUserPhoneNumber(phoneNumber: String): String
    suspend fun verifyUserPhoneNumber(phoneNumber: String, otp: String): String

    suspend fun updatePassword(oldPassword: String, newPassword: String): String
}