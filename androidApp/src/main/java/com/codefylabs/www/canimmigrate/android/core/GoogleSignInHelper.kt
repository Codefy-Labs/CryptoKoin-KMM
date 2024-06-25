package com.codefylabs.www.canimmigrate.android.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.codefylabs.www.canimmigrate.auth.domain.models.GoogleUser
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.Scopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleSignInHelper {

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    suspend fun getGoogleUser(
        context: Context,
        account: GoogleSignInAccount,
        onSuccess: (GoogleUser) -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val credential = GoogleAuthProvider.getCredential(account.idToken.toString(), null)
            firebaseAuth.signInWithCredential(credential).addOnCanceledListener {
                onError("")
            }.addOnSuccessListener {

                val scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE
                CoroutineScope(Dispatchers.IO).launch {
                   val accessToken =  GoogleAuthUtil.getToken(context, account.account!!, scope, Bundle())

                    val user = GoogleUser(
                        idToken = accessToken,
                        displayName = it.user?.displayName.toString(),
                        profilePicUrl = it.user?.photoUrl.toString()
                    )
                    onSuccess(user)
                }
            }.addOnFailureListener {
                Log.e("GoogleSignInHelper","AddOnFailureListener -> ${it.message}")
                it.printStackTrace()
                onError(it.localizedMessage ?: it.message ?: "Oops! An error occurred.")
            }
        } catch (e: Exception) {
            Log.e("GoogleSignInHelper","LoginWithGoogle : Exception Caught -> ${e.message}")
            onError(e.localizedMessage ?: e.message ?: "Oops! An error occurred.")
        }
    }
}