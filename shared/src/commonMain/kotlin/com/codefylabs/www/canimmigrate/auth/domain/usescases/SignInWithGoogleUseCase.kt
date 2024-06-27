package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.entities.UserInfo
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface SignInWithGoogleUseCase : suspend (String) -> Either<UserInfo>


class SignInWithGoogleUseCaseImpl constructor(
    private val repo: AuthRepository,
) : SignInWithGoogleUseCase {

    override suspend fun invoke(idToken: String): Either<UserInfo> = runCatching {
        val response = repo.signInWithGoogle(idToken)
        Either.Success(response)
    }.getOrElse {
        Either.Error(it.message ?: "Something went wrong!")
    }

}