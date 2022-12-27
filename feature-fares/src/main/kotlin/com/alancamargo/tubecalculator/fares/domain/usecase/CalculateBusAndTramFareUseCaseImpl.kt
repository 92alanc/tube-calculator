package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.fares.domain.repository.FaresRepository
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

internal class CalculateBusAndTramFareUseCaseImpl @Inject constructor(
    private val repository: FaresRepository
) : CalculateBusAndTramFareUseCase {

    override fun invoke(busAndTramJourneyCount: Int): String {
        val fare = BigDecimal(busAndTramJourneyCount) * repository.getBusAndTramBaseFare()
        val formattedValue = fare.setScale(2, RoundingMode.HALF_UP)
        return "£$formattedValue"
    }
}
