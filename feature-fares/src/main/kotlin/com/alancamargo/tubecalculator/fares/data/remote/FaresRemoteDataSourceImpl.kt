package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.log.Logger
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
    private val remoteDatabase: RemoteDatabase,
    private val logger: Logger
) : FaresRemoteDataSource {

    override suspend fun getRailFares(origin: Station, destination: Station): RailFaresResult {
        val documentId = "${origin.id}#${destination.id}"

        return try {
            val response = service.getRailFares(
                originId = origin.id,
                destinationId = destination.id
            )

            return if (response.isSuccessful) {
                handleSuccess(documentId, response)
            } else {
                logger.debug("Origin: ${origin.id}. Destination: ${destination.id} Response: ${response.code()} - ${response.errorBody()}")
                fetchFromRemoteDatabase(documentId)
            }
        } catch (serviceError: Throwable) {
            logger.error(serviceError)
            fetchFromRemoteDatabase(documentId, serviceError)
        }
    }

    private suspend fun fetchFromRemoteDatabase(
        documentId: String,
        serviceError: Throwable? = null
    ): RailFaresResult {
        return try {
            val response = remoteDatabase.load(
                collectionName = REMOTE_DATABASE_COLLECTION_NAME,
                documentId = documentId,
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )

            return response?.let {
                val railFares = it.fareList.map { fare -> fare.toDomain() }
                RailFaresResult.Success(railFares)
            } ?: serviceError?.let { throw it } ?: RailFaresResult.GenericError
        } catch (t: Throwable) {
            logger.error(t)
            serviceError?.let { throw it } ?: RailFaresResult.GenericError
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
            fetchFromRemoteDatabase(documentId)
        }
    }
}
