package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.core.extensions.roundUpAsMoney
import com.alancamargo.tubecalculator.fares.domain.model.FareRoot
import java.math.BigDecimal
import javax.inject.Inject

internal class CalculateCheapestFareUseCaseImpl @Inject constructor(
) : CalculateCheapestFareUseCase {

    override fun invoke(
        railFares: List<FareRoot.RailFare>,
        busAndTramFare: FareRoot.BusAndTramFare?
    ): String {
        val cheapestRailFare = railFares.minOf { railFare ->
            railFare.fareOptions.minOf { fareOption ->
                fareOption.tickets.minOf { ticket ->
                    BigDecimal(ticket.cost)
                }
            }
        }

        val busAndTramFareValue = busAndTramFare?.fare?.let(::BigDecimal) ?: BigDecimal.ZERO

        val sum = cheapestRailFare + busAndTramFareValue
        return sum.roundUpAsMoney()
    }
}
