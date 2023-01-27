package com.alancamargo.tubecalculator.fares.data.service

import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface RailFaresService {

    @GET("StopPoint/{origin}/FareTo/{destination}")
    suspend fun getRailFares(
        @Path("origin") originId: String,
        @Path("destination") destinationId: String
    ): Response<List<RailFareResponse>>
}
