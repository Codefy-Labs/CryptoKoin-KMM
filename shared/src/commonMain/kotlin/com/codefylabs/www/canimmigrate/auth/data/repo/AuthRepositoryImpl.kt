package com.codefylabs.www.canimmigrate.auth.data.repo

import com.codefylabs.www.canimmigrate.auth.data.local.AuthPersistence
import com.codefylabs.www.canimmigrate.auth.data.local.entity.LocalDataObject
import com.codefylabs.www.canimmigrate.auth.data.local.entity.SurveyDataObject
import com.codefylabs.www.canimmigrate.auth.data.remote.api.AuthAPI
import com.codefylabs.www.canimmigrate.core.data.models.ApiWrapper
import com.codefylabs.www.canimmigrate.core.util.NetworkResult
import com.codefylabs.www.canimmigrate.core.util.toObject
import com.codefylabs.www.canimmigrate.auth.domain.entities.LocalData
import com.codefylabs.www.canimmigrate.auth.domain.entities.Session
import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey
import com.codefylabs.www.canimmigrate.auth.domain.entities.UserInfo
import com.codefylabs.www.canimmigrate.auth.domain.entities.toLocalData
import com.codefylabs.www.canimmigrate.auth.domain.entities.toRealmObject
import com.codefylabs.www.canimmigrate.auth.domain.entities.toSession
import com.codefylabs.www.canimmigrate.auth.domain.entities.toSurveyObject
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class AuthRepositoryImpl constructor(
    private val api: AuthAPI,
    private val prefs: AuthPersistence,
    private val json: Json,
) : AuthRepository {


    override suspend fun login(email: String, password: String): UserInfo =
        when (val result = api.signIn(email, password)) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<Session>>(result.data.decodeToString())
                apiData.response?.copy(email = email)?.let { session ->
                    saveUserSession(session)
                    UserInfo(
                        id = session.userId,
                        userName = "User",
                        email = session.email,
                        phone_number = session.phoneNumber,
                    )
                } ?: throw Error(apiData.message)
            }
        }

    override suspend fun signup(username: String, email: String, password: String): String =
        when (val result = api.signUp(name = username, email = email, password = password)) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.response ?: apiData.message.toString()
            }
        }

    override suspend fun logout() {
        prefs.clearSession()
    }

    override suspend fun forgetPassword(email: String): String {
        return when (val result = api.forgetPassword(email)) {
            is NetworkResult.Error -> {
                throw result.error
            }

            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.message.toString()
            }
        }
    }

    override suspend fun resetPassword(
        email: String,
        confirmationCode: String,
        newPassword: String,
    ): String {
        return when (val result = api.resetPassword(
            email = email,
            newPassword = newPassword,
            verificationCode = confirmationCode
        )) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.response ?: apiData.message.toString()
            }
        }
    }

    override suspend fun resendCode(email: String) {

    }

    override suspend fun saveUserSession(session: Session) {
        prefs.writeSession(session.toRealmObject())
    }

    override suspend fun getUserSession(): Session? {
        return prefs.getSession()?.toSession()
    }

    override suspend fun getSessionFlow(): Flow<Session?> {
        return prefs.getSessionFlow().map { it?.toSession() }
    }

    override suspend fun deleteAccount() {
        when (val result = api.removeAccount()) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> Unit
        }
    }

    override fun getLocalData(): LocalData {
        return prefs.getLocalData()?.toLocalData() ?: LocalData()
    }

    override suspend fun saveOnboardingSurvey(
        survey: List<Survey>,
    ): String {
        val localDataObject = prefs.getLocalData() ?: LocalDataObject()
        localDataObject.isLaunchOnboardingFinished = true
        val updateSurvey =  (localDataObject.onboardingSurvey?.toMutableList()
            ?: mutableListOf()).also {
            it.addAll(survey.map { survey -> survey.toSurveyObject() })
        }
        localDataObject.onboardingSurvey = updateSurvey.toRealmList()
        prefs.writeLocalData(localDataObject)

        return if (prefs.isLoggedIn()) {
            when (val result = api.updateOnboardingSurvey(
                survey
            )) {
                is NetworkResult.Error -> throw result.error
                is NetworkResult.Success -> {
                    val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                    apiData.response ?: apiData.message.toString()
                }
            }
        } else ""

    }

    override suspend fun sendOtpOnUserPhoneNumber(phoneNumber: String): String {
        return when (val result = api.sendOtpOnUserPhoneNumber(
            phoneNumber = phoneNumber
        )) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.response ?: apiData.message.toString()
            }
        }
    }

    override suspend fun resendOtpOnUserPhoneNumber(phoneNumber: String): String {
        return when (val result = api.resendOtpOnUserPhoneNumber(
            phoneNumber = phoneNumber
        )) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.response ?: apiData.message.toString()
            }
        }
    }

    override suspend fun verifyUserPhoneNumber(phoneNumber: String, otp: String): String {
        return when (val result = api.verifyUserPhoneNumber(
            phoneNumber = phoneNumber,
            otp = otp
        )) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {

                // Currently Locally updated phone number, until the UserInfo api is not ready. After then remove it.
                getUserSession()?.copy(phoneNumber = phoneNumber)?.toRealmObject()?.let {
                    prefs.writeSession(it)
                }

                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.response ?: apiData.message.toString()
            }
        }
    }

    override suspend fun updatePassword(oldPassword: String, newPassword: String): String {
        return when (val result = api.updatePassword(
            oldPassword = oldPassword,
            newPassword = newPassword
        )) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> {
                val apiData = json.toObject<ApiWrapper<String?>>(result.data.decodeToString())
                apiData.response ?: apiData.message.toString()
            }
        }
    }


}