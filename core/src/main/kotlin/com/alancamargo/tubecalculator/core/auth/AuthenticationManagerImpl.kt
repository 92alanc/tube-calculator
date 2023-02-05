package com.alancamargo.tubecalculator.core.auth

import com.alancamargo.tubecalculator.core.log.Logger
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class AuthenticationManagerImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val logger: Logger
) : AuthenticationManager{

    override suspend fun authenticateAnonymously() {
        firebaseAuth.signInAnonymously().await()

        if (firebaseAuth.currentUser == null) {
            logger.debug("Authentication failed")
        }
    }
}
