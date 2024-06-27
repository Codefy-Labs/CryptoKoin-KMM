package com.codefylabs.www.canimmigrate.auth.data.remote.api

import com.codefylabs.www.canimmigrate.BaseConfig
import com.codefylabs.www.canimmigrate.auth.data.remote.models.OnboardingSurveyRequest
import com.codefylabs.www.canimmigrate.auth.data.remote.models.ResetPasswordRequest
import com.codefylabs.www.canimmigrate.auth.data.remote.models.SendOtpRequest
import com.codefylabs.www.canimmigrate.auth.data.remote.models.SigninRequest
import com.codefylabs.www.canimmigrate.auth.data.remote.models.SignupRequest
import com.codefylabs.www.canimmigrate.auth.data.remote.models.UpdatePasswordRequest
import com.codefylabs.www.canimmigrate.auth.data.remote.models.VerifyUserPhoneNumberRequest
import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey
import com.codefylabs.www.canimmigrate.core.data.remote.KmmAppKtorClient
import com.codefylabs.www.canimmigrate.core.data.remote.submitFormWithData
import com.codefylabs.www.canimmigrate.core.data.remote.submitWithBody
import com.codefylabs.www.canimmigrate.core.data.remote.toResult
import com.codefylabs.www.canimmigrate.core.util.NetworkResult

class AuthApiImp(
    private val client: KmmAppKtorClient,
) : AuthAPI {

    override suspend fun signIn(email: String, password: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/auth/signin",
            body = SigninRequest(email = email.lowercase(), password = password)
        ).toResult()

    override suspend fun signInWithGoogle(token: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/auth/signin-with-google",
            body = mapOf("idToken" to token)
        ).toResult()

    override suspend fun signUp(name: String, email: String, password: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/auth/signup",
            body = SignupRequest(name = name, email = email.lowercase(), password = password),
        ).toResult()

    override suspend fun forgetPassword(email: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/forgot-password/send-otp?email=${email.lowercase()}",
            body = mapOf<String,String>()
        ).toResult()

    override suspend fun resetPassword(
        email: String,
        verificationCode: String,
        newPassword: String,
    ): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/forgot-password/reset",
            body = ResetPasswordRequest(
                email = email.lowercase(),
                password = newPassword,
                otp = verificationCode
            )
        ).toResult()



    override suspend fun removeAccount(): NetworkResult =
        client.submitFormWithData(
            url = "${BaseConfig.BASE_URL}/remove-account",
            formData = { }
        ).toResult()

    override suspend fun sendOtpOnUserPhoneNumber(phoneNumber: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/validate-phone-number",
            body = SendOtpRequest(phoneNumber)
        ).toResult()

    override suspend fun resendOtpOnUserPhoneNumber(phoneNumber: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/resendotp-userphonenumber",
            body = SendOtpRequest(phoneNumber)
        ).toResult()

    override suspend fun verifyUserPhoneNumber(phoneNumber: String, otp: String): NetworkResult =
        client.submitWithBody(
            url = "${BaseConfig.BASE_URL}/save-phone-number",
            body = VerifyUserPhoneNumberRequest(phoneNumber, otp)
        ).toResult()

    override suspend fun updatePassword(oldPassword: String, newPassword: String): NetworkResult =
        client.submitWithBody(
            "${BaseConfig.BASE_URL}/update-password",
            body = UpdatePasswordRequest(oldPassword = oldPassword, newPassword = newPassword)
        ).toResult()

    override suspend fun updateOnboardingSurvey(list: List<Survey>): NetworkResult =
        client.submitWithBody(
            "${BaseConfig.BASE_URL}/update-onboarding-survey",
            body = OnboardingSurveyRequest(list)
        ).toResult()


}