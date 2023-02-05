package com.alancamargo.tubecalculator.core.auth

import com.alancamargo.tubecalculator.core.log.Logger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

internal class AuthenticationManagerImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val logger: Logger
) : AuthenticationManager{

    override fun authenticateAnonymously() {
        if (!firebaseAuth.currentUser.isSignedIn()) {
            firebaseAuth.signInAnonymously().addOnFailureListener {
                logger.debug("Authentication failed")
                logger.error(it)
            }
        }
    }

    private fun FirebaseUser?.isSignedIn(): Boolean = this != null
}
