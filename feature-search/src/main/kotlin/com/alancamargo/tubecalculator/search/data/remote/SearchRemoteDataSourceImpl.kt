package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.search.data.api.SearchService
import com.alancamargo.tubecalculator.search.data.mapping.toDomain
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
import retrofit2.Response
import javax.inject.Inject

internal class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchRemoteDataSource {

    override suspend fun searchStation(query: String): List<Station> {
        val response = service.searchStation(query = query)

        return if (response.isSuccessful) {
            handleSuccess(response)
        } else {
            handleError(response)
        }
    }

    private fun handleSuccess(
        response: Response<StationSearchResultsResponse>
    ): List<Station> {
        return response.body()?.matches?.let {
            it.map { station -> station.toDomain() }
        } ?: run {
            emptyList()
        }
    }

    private fun handleError(
        response: Response<StationSearchResultsResponse>
    ): List<Station> {
        throw Throwable()
        /*if (response.isRequestError()) {
            emit(StationListResult.GenericError)
        } else if (response.isServerError()) {
            emit(StationListResult.ServerError)
        } else {
            emit(StationListResult.GenericError)
        }*/
    }
}
