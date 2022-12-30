package com.alancamargo.tubecalculator.fares.data.service

import com.alancamargo.tubecalculator.fares.data.model.responses.FareListRootResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface FaresService {

    @GET("StopPoint/{origin}/FareTo/{destination}")
    suspend fun getFares(
        @Path("origin") originId: String,
        @Path("destination") destinationId: String
    ): Response<List<FareListRootResponse>>
}
