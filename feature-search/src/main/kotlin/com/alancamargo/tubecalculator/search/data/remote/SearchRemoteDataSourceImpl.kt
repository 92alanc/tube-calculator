package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.core.extensions.isRequestError
import com.alancamargo.tubecalculator.core.extensions.isServerError
import com.alancamargo.tubecalculator.search.data.api.SearchService
import com.alancamargo.tubecalculator.search.data.mapping.toDomain
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import retrofit2.Response
import javax.inject.Inject

internal class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchRemoteDataSource {

    override suspend fun searchStation(query: String): StationListResult {
        val response = service.searchStation(query = query)

        return if (response.isSuccessful) {
            handleSuccess(response)
        } else {
            handleError(response)
        }
    }

    private fun handleSuccess(
        response: Response<StationSearchResultsResponse>
    ): StationListResult {
        return response.body()?.matches?.let {
            val stations = it.map { station -> station.toDomain() }
            StationListResult.Success(stations)
        } ?: run {
            StationListResult.Success(emptyList())
        }
    }

    private fun handleError(
        response: Response<StationSearchResultsResponse>
    ): StationListResult {
        return if (response.isRequestError()) {
            StationListResult.GenericError
        } else if (response.isServerError()) {
            StationListResult.ServerError
        } else {
            StationListResult.GenericError
        }
    }
}
