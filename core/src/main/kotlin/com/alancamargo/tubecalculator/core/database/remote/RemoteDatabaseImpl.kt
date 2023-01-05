package com.alancamargo.tubecalculator.core.database.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class RemoteDatabaseImpl(
    private val firestore: FirebaseFirestore
) : RemoteDatabase {

    override fun <T : Any> save(
        collectionName: String,
        documentId: String,
        data: T
    ): Flow<Unit> = flow {
        firestore.collection(collectionName)
            .document(documentId)
            .set(data, SetOptions.merge())
            .await()
    }

    override fun loadJson(collectionName: String, documentId: String): Flow<String?> = flow {
        val query = firestore.collection(collectionName)
            .get().await()
        val data = query.documents.find { it.id == documentId }?.data

        val json = data?.let(Json::encodeToString)
        emit(json)
    }
}
