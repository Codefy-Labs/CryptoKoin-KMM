package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface LogoutUser : suspend () -> Either<Unit>

class LogoutUserImpl  constructor(
    private val repo: AuthRepository,
) : LogoutUser {
    override suspend fun invoke(): Either<Unit> = runCatching {
        Either.Success(repo.logout())
    }.getOrElse { Either.Error(it.message.toString()) }
}