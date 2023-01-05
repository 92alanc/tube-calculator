package com.alancamargo.tubecalculator.core.database.remote

import kotlinx.coroutines.flow.Flow

interface RemoteDatabase {

    fun <T : Any> save(
        collectionName: String,
        documentId: String,
        data: T
    ): Flow<Unit>

    fun loadJson(collectionName: String, documentId: String): Flow<String?>
}
