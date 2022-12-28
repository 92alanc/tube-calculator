package com.alancamargo.tubecalculator.search.data.remote

import com.alancamargo.tubecalculator.core.extensions.isRequestError
import com.alancamargo.tubecalculator.core.extensions.isServerError
import com.alancamargo.tubecalculator.search.data.api.SearchService
import com.alancamargo.tubecalculator.search.data.mapping.toDomain
import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

internal class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchRemoteDataSource {

    override fun searchStation(query: String): Flow<StationListResult> = flow {
        val response = service.searchStation(query = query)

        if (response.isSuccessful) {
            handleSuccess(response)
        } else {
            handleError(response)
        }
    }

    private suspend fun FlowCollector<StationListResult>.handleSuccess(
        response: Response<StationSearchResultsResponse>
    ) {
        response.body()?.let { body ->
            body.matches?.let {
                if (it.isEmpty()) {
                    emit(StationListResult.Empty)
                } else {
                    val stations = it.map { station -> station.toDomain() }
                    emit(StationListResult.Success(stations))
                }
            } ?: run {
                emit(StationListResult.Empty)
            }
        } ?: run {
            emit(StationListResult.Empty)
        }
    }

    private suspend fun FlowCollector<StationListResult>.handleError(
        response: Response<StationSearchResultsResponse>
    ) {
        if (response.isRequestError()) {
            emit(StationListResult.GenericError)
        } else if (response.isServerError()) {
            emit(StationListResult.ServerError)
        } else {
            emit(StationListResult.GenericError)
        }
    }
}
