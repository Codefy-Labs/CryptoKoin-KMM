package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either

interface UserPhoneNoUseCase {
   suspend fun sendOtpOnUserPhoneNumber(phoneNumber : String) : Either<String>
    suspend fun resendOtpOnUserPhoneNumber(phoneNumber : String) : Either<String>
   suspend fun verifyUserPhoneNumber(phoneNumber : String, otp : String) : Either<String>
}

class UserPhoneNoUseCaseImpl(
    private val repo: AuthRepository
) : UserPhoneNoUseCase {
    override suspend fun sendOtpOnUserPhoneNumber(phoneNumber: String): Either<String> =

        runCatching {
            val result = repo.sendOtpOnUserPhoneNumber(phoneNumber)
            Either.Success(result)
        }.getOrElse {
            it.cause?.printStackTrace()
            Either.Error(it.message ?: "Something went wrong!")
        }

    override suspend fun resendOtpOnUserPhoneNumber(phoneNumber: String): Either<String> =

        runCatching {
            val result = repo.resendOtpOnUserPhoneNumber(phoneNumber)
            Either.Success(result)
        }.getOrElse {
            it.cause?.printStackTrace()
            Either.Error(it.message ?: "Something went wrong!")
        }

    override suspend fun verifyUserPhoneNumber(phoneNumber: String, otp: String): Either<String> =

        runCatching {
            val result = repo.verifyUserPhoneNumber(phoneNumber,otp)
            Either.Success(result)
        }.getOrElse {
            it.cause?.printStackTrace()
            Either.Error(it.message ?: "Something went wrong!")
        }


}