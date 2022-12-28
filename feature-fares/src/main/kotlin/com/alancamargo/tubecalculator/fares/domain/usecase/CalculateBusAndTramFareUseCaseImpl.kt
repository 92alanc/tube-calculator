package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

internal class CalculateBusAndTramFareUseCaseImpl @Inject constructor(
    private val repository: FaresRepository
) : CalculateBusAndTramFareUseCase {

    override fun invoke(busAndTramJourneyCount: Int): String? {
        val fare = BigDecimal(busAndTramJourneyCount) * repository.getBusAndTramBaseFare()

        return if (fare.toDouble() == 0.0) {
            null
        } else {
            val formattedValue = fare.setScale(2, RoundingMode.HALF_UP)
            "£$formattedValue"
        }
    }
}
