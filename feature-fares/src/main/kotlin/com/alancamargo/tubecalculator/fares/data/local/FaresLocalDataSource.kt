package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot

internal interface FaresLocalDataSource {

    suspend fun getFares(origin: Station, destination: Station): FareListResult

    suspend fun saveFares(origin: Station, destination: Station, fares: List<FareListRoot>)

    suspend fun clearCache()
}
