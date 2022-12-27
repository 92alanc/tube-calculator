package com.alancamargo.tubecalculator.fares.testtools

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.*

internal const val STATION_ID = "12345"

private const val HEADER = "Single fare finder"

internal fun stubFareListRootResponse(emptyFareList: Boolean = false): FareListRootResponse {
    val fares = if (emptyFareList) {
        emptyList()
    } else {
        stubFareResponseList()
    }

    return FareListRootResponse(
        header = HEADER,
        fares = fares
    )
}

internal fun stubFareListRoot() = stubFareListRootResponse().toDomain()

internal fun stubStation() = Station(
    id = STATION_ID,
    name = "Romford",
    modes = listOf(Mode.ELIZABETH_LINE, Mode.OVERGROUND, Mode.NATIONAL_RAIL),
    zones = listOf(6)
)

private fun stubFareResponseList() = listOf(
    FareResponse(
        label = "Default route",
        origin = "Romford",
        destination = "Camden Road",
        description = "Default route",
        passengerType = "Adult",
        tickets = stubTicketResponseList(),
        messages = stubFareMessageResponseList()
    )
)

private fun stubTicketResponseList() = listOf(
    TicketResponse(
        type = stubTicketTypeResponse(),
        time = stubTicketTimeResponse(),
        cost = "3.50"
    )
)

private fun stubFareMessageResponseList() = listOf(
    FareMessageResponse(message = "Pay your fares or we will throw you in jail"),
    FareMessageResponse(message = "")
)

private fun stubTicketTypeResponse() = TicketTypeResponse(label = "Pay as you go")

private fun stubTicketTimeResponse() = TicketTimeResponse(
    label = "Off Peak",
    description = "At all other times including public holidays."
)
