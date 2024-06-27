package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.entities.UserInfo
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either

interface SignInUseCase : suspend (String, String) -> Either<UserInfo>


class SignInUseCaseImpl constructor(
    private val repo: AuthRepository
) : SignInUseCase {

    override suspend fun invoke(email: String, password: String): Either<UserInfo> = runCatching {
        val response = repo.signIn(email, password)
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}