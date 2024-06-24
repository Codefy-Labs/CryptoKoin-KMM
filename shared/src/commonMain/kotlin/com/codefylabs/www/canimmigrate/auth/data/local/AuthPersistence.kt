package com.codefylabs.www.canimmigrate.auth.data.local

import com.codefylabs.www.canimmigrate.auth.data.local.entity.LocalDataObject
import com.codefylabs.www.canimmigrate.auth.data.local.entity.SessionObject
import kotlinx.coroutines.flow.Flow

interface AuthPersistence {
    fun isLoggedIn(): Boolean


    suspend fun getSession(): SessionObject?

    fun getLocalData(): LocalDataObject?

    suspend fun writeLocalData(localDataObject: LocalDataObject)

    suspend fun writeSession(sessionObject: SessionObject)

    suspend fun clearSession()
    fun getSessionFlow(): Flow<SessionObject?>

}