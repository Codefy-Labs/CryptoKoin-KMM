package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface ResetPasswordUseCase : suspend (String, String, String) -> Either<String>

class ResetPasswordUseCaseImpl constructor(
    private val repo: AuthRepository
) : ResetPasswordUseCase {
    override suspend fun invoke(email: String, password: String, code: String): Either<String> =
        runCatching {
            val message = repo.resetPassword(
                email = email,
                newPassword = password,
                confirmationCode = code
            )
            Either.Success(message)
        }.getOrElse { Either.Error((it as? Exception)?.message ?: "Something went wrong!") }

}