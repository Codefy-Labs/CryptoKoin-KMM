package com.codefylabs.www.canimmigrate.auth.data.local

import com.codefylabs.www.canimmigrate.auth.data.local.entity.LocalDataObject
import com.codefylabs.www.canimmigrate.auth.data.local.entity.SessionObject
import io.github.aakira.napier.Napier
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AuthPersistenceImpl(private val db: Realm) : AuthPersistence {

    override fun isLoggedIn(): Boolean {
        val sessions = db.query<SessionObject>().find().lastOrNull()
        return sessions?.accessToken?.isNotEmpty() ?: false
    }

    override suspend fun getSession(): SessionObject? {
        val sessions = db.query<SessionObject>().find()
        return if (sessions.isEmpty()) {
            null
        } else {
            sessions.last()
        }
    }

    override fun getLocalData(): LocalDataObject {
        return db.query<LocalDataObject>().find().lastOrNull() ?: LocalDataObject.init()
    }

    override suspend fun writeLocalData(localDataObject: LocalDataObject) {
        db.writeBlocking {
            copyToRealm(localDataObject, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override fun getSessionFlow(): Flow<SessionObject?> {
        return db.query<SessionObject>().asFlow()
            .map {
                (it.list.lastOrNull())
            }
    }

    override suspend fun writeSession(sessionObject: SessionObject) {
        Napier.d("WriteSession -> ${sessionObject.toString()}")
        db.writeBlocking {
            copyToRealm(sessionObject, updatePolicy = UpdatePolicy.ALL)
        }
    }

    override suspend fun clearSession() {
        db.writeBlocking {
            val session = query<SessionObject>().find()
            delete(session)

            val localData = query<LocalDataObject>().find()
            delete(localData)
        }
    }


}