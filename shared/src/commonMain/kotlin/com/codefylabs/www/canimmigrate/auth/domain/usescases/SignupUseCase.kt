package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface SignupUseCase : suspend (String, String, String) -> Either<String>

class SignupUseCaseImpl constructor(
    private val repo: AuthRepository
) : SignupUseCase {
    override suspend fun invoke(userName: String, email: String, password: String): Either<String> =
        runCatching {
            val result = repo.signup(userName, email, password)
            Either.Success(result)
        }.getOrElse {
            it.cause?.printStackTrace()
            Either.Error(it.message ?: "Something went wrong!")
        }
}