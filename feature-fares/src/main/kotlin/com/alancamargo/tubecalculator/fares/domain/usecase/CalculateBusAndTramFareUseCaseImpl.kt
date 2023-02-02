package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.core.extensions.roundUpAsMoney
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import java.math.BigDecimal
import javax.inject.Inject

internal class CalculateBusAndTramFareUseCaseImpl @Inject constructor(
    private val repository: FaresRepository
) : CalculateBusAndTramFareUseCase {

    override fun invoke(busAndTramJourneyCount: Int): Fare.BusAndTramFare? {
        val fare = BigDecimal(busAndTramJourneyCount) * repository.getBusAndTramBaseFare()

        return if (fare.toDouble() == 0.0) {
            null
        } else {
            val formattedValue = fare.roundUpAsMoney()
            Fare.BusAndTramFare(formattedValue)
        }
    }
}
