package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface ForgotPasswordUseCase : suspend (String) -> Either<String>

class ForgotPasswordUseCaseImpl constructor(
    private val repo: AuthRepository
) : ForgotPasswordUseCase {
    override suspend fun invoke(email: String): Either<String> = runCatching {
        val message = repo.forgetPassword(email)
        Either.Success(message)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }
}