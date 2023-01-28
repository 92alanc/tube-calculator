package com.alancamargo.tubecalculator.fares.domain.usecase

import com.alancamargo.tubecalculator.core.extensions.roundUpAsMoney
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import java.math.BigDecimal
import javax.inject.Inject

internal class CalculateCheapestTotalFareUseCaseImpl @Inject constructor(
) : CalculateCheapestTotalFareUseCase {

    override fun invoke(fares: List<Fare>): String {
        val cheapestRailFare = fares.filterIsInstance<Fare.RailFare>().minOf { fare ->
            fare.fareOptions.minOf { fareOption ->
                fareOption.tickets.minOf { ticket ->
                    ticket.cost.toDouble()
                }
            }
        }

        val busAndTramFare = fares.filterIsInstance<Fare.BusAndTramFare>().sumOf { fare ->
            fare.cost.toDouble()
        }

        val sum = cheapestRailFare + busAndTramFare
        val cheapestTotalFare = BigDecimal(sum)
        return cheapestTotalFare.roundUpAsMoney()
    }
}
