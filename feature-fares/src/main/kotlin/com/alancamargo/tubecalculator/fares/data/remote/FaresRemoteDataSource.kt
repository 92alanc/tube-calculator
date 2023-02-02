package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult

internal interface FaresRemoteDataSource {

    suspend fun getRailFares(origin: Station, destination: Station): RailFaresResult
}
