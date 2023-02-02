package com.alancamargo.tubecalculator.core.database.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class RemoteDatabaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteDatabase {

    override suspend fun <T : Any> save(collectionName: String, documentId: String, data: T) {
        firestore.collection(collectionName)
            .document(documentId)
            .set(data, SetOptions.merge())
            .await()
    }

    override suspend fun <T> load(
        collectionName: String,
        documentId: String,
        outputClass: Class<T>
    ): T? {
        val query = firestore.collection(collectionName)
            .get().await()
        val document = query.documents.find { it.id == documentId }

        return document?.toObject(outputClass)
    }
}
