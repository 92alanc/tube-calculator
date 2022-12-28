package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.extensions.isRequestError
import com.alancamargo.tubecalculator.core.extensions.isServerError
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.FareListRootResponse
import com.alancamargo.tubecalculator.fares.data.service.FaresService
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

internal class FaresRemoteDataSourceImpl @Inject constructor(
    private val service: FaresService
) : FaresRemoteDataSource {

    override fun getFares(
        origin: Station,
        destination: Station
    ): Flow<FareListResult> = flow {
        val response = service.getFares(
            originId = origin.id,
            destinationId = destination.id
        )

        if (response.isSuccessful) {
            handleSuccess(response)
        } else {
            handleError(response)
        }
    }

    private suspend fun FlowCollector<FareListResult>.handleSuccess(
        response: Response<List<FareListRootResponse>>
    ) {
        response.body()?.let { body ->
            if (body.isEmpty()) {
                emit(FareListResult.Empty)
            } else {
                val fareList = body.map { it.toDomain() }
                emit(FareListResult.Success(fareList))
            }
        } ?: run {
            emit(FareListResult.Empty)
        }
    }

    private suspend fun FlowCollector<FareListResult>.handleError(
        response: Response<List<FareListRootResponse>>
    ) {
        if (response.isRequestError()) {
            emit(FareListResult.GenericError)
        } else if (response.isServerError()) {
            emit(FareListResult.ServerError)
        } else {
            emit(FareListResult.GenericError)
        }
    }
}
