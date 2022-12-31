package com.alancamargo.tubecalculator.fares.data.remote

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult

internal interface FaresRemoteDataSource {

    suspend fun getFares(origin: Station, destination: Station): FareListResult
}
