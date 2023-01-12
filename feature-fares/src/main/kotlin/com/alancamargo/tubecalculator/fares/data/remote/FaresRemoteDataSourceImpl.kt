package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.extensions.isRequestError
import com.alancamargo.tubecalculator.core.extensions.isServerError
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.database.RemoteDatabaseFareListWrapper
import com.alancamargo.tubecalculator.fares.data.model.responses.FareListRootResponse
import com.alancamargo.tubecalculator.fares.data.service.FaresService
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import retrofit2.Response
import javax.inject.Inject

private const val REMOTE_DATABASE_COLLECTION_NAME = "fares"

internal class FaresRemoteDataSourceImpl @Inject constructor(
    private val service: FaresService,
    private val remoteDatabase: RemoteDatabase
) : FaresRemoteDataSource {

    override suspend fun getFares(origin: Station, destination: Station): FareListResult {
        val documentId = "${origin.id}#${destination.id}"

        return try {
            val response = remoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )

            response?.let {
                val fareList = it.fareList.map { fare -> fare.toDomain() }
                FareListResult.Success(fareList)
            } ?: run {
                fetchFromService(origin, destination, documentId)
            }
        } catch (t: Throwable) {
            fetchFromService(origin, destination, documentId)
        }
    }

    private suspend fun fetchFromService(
        origin: Station,
        destination: Station,
        documentId: String
    ): FareListResult {
        val response = service.getFares(
            originId = origin.id,
            destinationId = destination.id
        )

        return if (response.isSuccessful) {
            handleSuccess(documentId, response)
        } else {
            handleError(response)
        }
    }

    private suspend fun handleSuccess(
        documentId: String,
        response: Response<List<FareListRootResponse>>
    ): FareListResult {
        return response.body()?.let { body ->
            if (body.isEmpty()) {
                FareListResult.InvalidQueryError
            } else {
                val data = RemoteDatabaseFareListWrapper(body)
                remoteDatabase.save(REMOTE_DATABASE_COLLECTION_NAME, documentId, data)
                val fareList = body.map { it.toDomain() }
                FareListResult.Success(fareList)
            }
        } ?: run {
            FareListResult.GenericError
        }
    }

    private fun handleError(response: Response<List<FareListRootResponse>>): FareListResult {
        return if (response.isRequestError()) {
            FareListResult.GenericError
        } else if (response.isServerError()) {
            FareListResult.ServerError
        } else {
            FareListResult.GenericError
        }
    }
}
