package com.alancamargo.tubecalculator.core.network

interface ApiProvider {

    fun <T> provideService(clazz: Class<T>): T
}
