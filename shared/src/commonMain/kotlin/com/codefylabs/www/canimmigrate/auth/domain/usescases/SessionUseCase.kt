package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.entities.Session
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.domain.util.flow.CommonFlow
import com.codefylabs.www.canimmigrate.core.domain.util.flow.toCommonFlow
import kotlinx.coroutines.flow.map


interface SessionUseCase {

    suspend fun getSession(): Session?
    suspend fun getSessionFlow(): CommonFlow<Session?>

    suspend fun isLoggedIn(): Boolean
    suspend fun isLoggedInFlow(): CommonFlow<Boolean>

    suspend fun getUserEmail(): String?

    suspend fun getUserPhoneNumber(): String?


    suspend fun logout()

}


class SessionUseCaseImpl(val repo: AuthRepository) : SessionUseCase {
    override suspend fun getSession(): Session? = repo.getUserSession()
    override suspend fun getSessionFlow(): CommonFlow<Session?> =
        repo.getSessionFlow().toCommonFlow()

    override suspend fun isLoggedIn(): Boolean {
        return repo.getUserSession()?.accessToken != null
    }
    override suspend fun isLoggedInFlow(): CommonFlow<Boolean> =
        repo.getSessionFlow().map { !it?.accessToken.isNullOrEmpty() }.toCommonFlow()

    override suspend fun getUserEmail(): String? = repo.getUserSession()?.email
    override suspend fun getUserPhoneNumber(): String? = repo.getUserSession()?.phoneNumber

    override suspend fun logout() {
        repo.logout()
    }

}