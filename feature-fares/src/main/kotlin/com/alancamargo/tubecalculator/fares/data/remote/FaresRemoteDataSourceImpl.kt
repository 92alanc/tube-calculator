package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.core.extensions.isRequestError
import com.alancamargo.tubecalculator.core.extensions.isServerError
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.responses.FareListRootResponse
import com.alancamargo.tubecalculator.fares.data.service.FaresService
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import retrofit2.Response
import javax.inject.Inject

internal class FaresRemoteDataSourceImpl @Inject constructor(
    private val service: FaresService
) : FaresRemoteDataSource {

    override suspend fun getFares(origin: Station, destination: Station): FareListResult {
        val response = service.getFares(
            originId = origin.id,
            destinationId = destination.id
        )

        return if (response.isSuccessful) {
            handleSuccess(response)
        } else {
            handleError(response)
        }
    }

    private fun handleSuccess(response: Response<List<FareListRootResponse>>): FareListResult {
        return response.body()?.let { body ->
            if (body.isEmpty()) {
                FareListResult.GenericError
            } else {
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
