package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either

interface UpdatePasswordUseCase : suspend (String, String) -> Either<String>

class UpdatePasswordUseCaseImpl constructor(
    private val repo: AuthRepository
) : UpdatePasswordUseCase {

    override suspend fun invoke(oldPassword: String, newPassword: String): Either<String> =
        runCatching {
            val response = repo.updatePassword(oldPassword = oldPassword, newPassword = newPassword)
            Either.Success(response)
        }.getOrElse {
            Either.Error(it.message ?: "Something went wrong!")
        }

}