package com.alancamargo.tubecalculator.core.database.remote

interface RemoteDatabase {

    suspend fun <T : Any> save(collectionName: String, documentId: String, data: T)

    suspend fun <T> load(collectionName: String, documentId: String, outputClass: Class<T>): T?
}
