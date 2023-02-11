package com.alancamargo.tubecalculator.fares.domain.repository

import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

internal interface FaresRepository {

    fun getRailFares(origin: Station, destination: Station): Flow<RailFaresResult>

    fun getBusAndTramBaseFare(): BigDecimal

    fun getBusAndTramDailyFareCap(): BigDecimal
}
