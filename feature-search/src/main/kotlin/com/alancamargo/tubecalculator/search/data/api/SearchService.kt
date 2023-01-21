package com.alancamargo.tubecalculator.search.data.api

import com.alancamargo.tubecalculator.search.data.model.StationSearchResultsResponse
import com.alancamargo.tubecalculator.search.data.model.StopPointListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface SearchService {

    @GET("StopPoint/Search/{query}")
    suspend fun searchStation(
        @Path("query") query: String,
        @Query("modes") modes: String = "dlr,elizabeth-line,national-rail,overground,tube",
        @Query("oysterOnly") isOysterOnly: Boolean = true,
        @Query("faresOnly") isLondonFaresOnly: Boolean = true
    ): Response<StationSearchResultsResponse>

    @GET("StopPoint/Mode/{modes}")
    suspend fun getAllStopPoints(
        @Path("modes") modes: String = "dlr,elizabeth-line,overground,tube"
    ): Response<StopPointListResponse>
}
