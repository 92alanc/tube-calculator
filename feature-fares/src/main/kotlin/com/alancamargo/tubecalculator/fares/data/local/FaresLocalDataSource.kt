package com.alancamargo.tubecalculator.fares.data.local

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot
import kotlinx.coroutines.flow.Flow

internal interface FaresLocalDataSource {

    fun getFares(origin: Station, destination: Station): Flow<FareListResult>

    fun saveFares(fares: List<FareListRoot>): Flow<Unit>
}
