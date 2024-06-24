package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.entities.UserInfo
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either

interface LoginUseCase : suspend (String, String) -> Either<UserInfo>


class LoginUseCaseImpl constructor(
    private val repo: AuthRepository
) : LoginUseCase {

    override suspend fun invoke(email: String, password: String): Either<UserInfo> = runCatching {
        val response = repo.login(email, password)
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}