package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult

internal interface FaresLocalDataSource {

    suspend fun getRailFares(origin: Station, destination: Station): RailFaresResult

    suspend fun saveRailFares(
        origin: Station,
        destination: Station,
        railFares: List<Fare.RailFare>
    )

    suspend fun clearCache()
}
