package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface DeleteUserAccount : suspend () -> Either<Unit>

class DeleteUserAccountImpl  constructor(
    private val repo: AuthRepository
) : DeleteUserAccount {
    override suspend fun invoke(): Either<Unit> = runCatching {
        Either.Success(repo.deleteAccount())
    }.getOrElse {
        Either.Error((it as? Exception)?.message ?: "Something went wrong!")
    }
}