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
        var fare = BigDecimal(busAndTramJourneyCount) * repository.getBusAndTramBaseFare()
        val cap = repository.getBusAndTramDailyFareCap()

        return if (fare.toDouble() == 0.0) {
            null
        } else {
            if (fare > cap) {
                fare = cap
            }

            val formattedValue = fare.roundUpAsMoney()
            Fare.BusAndTramFare(formattedValue)
        }
    }
}
