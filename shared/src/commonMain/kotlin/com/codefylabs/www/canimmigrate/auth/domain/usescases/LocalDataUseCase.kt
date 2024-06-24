package com.codefylabs.www.canimmigrate.auth.domain.usescases

import com.codefylabs.www.canimmigrate.auth.domain.entities.LocalData
import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey
import com.codefylabs.www.canimmigrate.auth.domain.repo.AuthRepository
import com.codefylabs.www.canimmigrate.core.util.Either


interface LocalDataUseCase {
    fun getLocalData(): LocalData
    suspend fun saveOnOnboardingSurvey(survey: List<Survey>): Either<String>
}


class LocalDataUseCaseImpl(
    private val repo: AuthRepository,
) : LocalDataUseCase {

    override fun getLocalData(): LocalData = runCatching {
        val response = repo.getLocalData()
        response
    }.getOrElse {
        LocalData()
    }

    override suspend fun saveOnOnboardingSurvey(survey: List<Survey>): Either<String> =
        runCatching {
            val response = repo.saveOnboardingSurvey(survey)
            Either.Success(response)
        }.getOrElse {
            Either.Error(it.message.toString())
        }
}