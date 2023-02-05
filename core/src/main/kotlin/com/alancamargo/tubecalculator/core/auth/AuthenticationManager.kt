package com.alancamargo.tubecalculator.core.auth

interface AuthenticationManager {

    suspend fun authenticateAnonymously()
}
