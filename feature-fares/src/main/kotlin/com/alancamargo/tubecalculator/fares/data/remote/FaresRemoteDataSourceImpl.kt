package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.extensions.isRequestError
import com.alancamargo.tubecalculator.core.extensions.isServerError
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.database.RemoteDatabaseFareListWrapper
import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
import com.alancamargo.tubecalculator.fares.data.service.RailFaresService
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import retrofit2.Response
import javax.inject.Inject

private const val REMOTE_DATABASE_COLLECTION_NAME = "fares"

internal class FaresRemoteDataSourceImpl @Inject constructor(
    private val service: RailFaresService,
    private val remoteDatabase: RemoteDatabase
) : FaresRemoteDataSource {

    override suspend fun getRailFares(origin: Station, destination: Station): RailFaresResult {
        val documentId = "${origin.id}#${destination.id}"

        return try {
            val response = remoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )

            response?.let {
                val railFares = it.fareList.map { fare -> fare.toDomain() }
                RailFaresResult.Success(railFares)
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
    ): RailFaresResult {
        val response = service.getRailFares(
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
        response: Response<List<RailFareResponse>>
    ): RailFaresResult {
        return response.body()?.let { body ->
            if (body.isEmpty()) {
                RailFaresResult.InvalidQueryError
            } else {
                val data = RemoteDatabaseFareListWrapper(body)
                remoteDatabase.save(REMOTE_DATABASE_COLLECTION_NAME, documentId, data)
                val railFares = body.map { it.toDomain() }
                RailFaresResult.Success(railFares)
            }
        } ?: run {
            RailFaresResult.GenericError
        }
    }

    private fun handleError(response: Response<List<RailFareResponse>>): RailFaresResult {
        return if (response.isRequestError()) {
            RailFaresResult.GenericError
        } else if (response.isServerError()) {
            RailFaresResult.ServerError
        } else {
            RailFaresResult.GenericError
        }
    }
}
