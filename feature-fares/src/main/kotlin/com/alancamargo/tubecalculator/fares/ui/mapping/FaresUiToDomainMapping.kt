package com.alancamargo.tubecalculator.fares.ui.mapping

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.FareOption
import com.alancamargo.tubecalculator.fares.domain.model.Ticket
import com.alancamargo.tubecalculator.fares.domain.model.TicketTime
import com.alancamargo.tubecalculator.fares.domain.model.TicketType
import com.alancamargo.tubecalculator.fares.ui.model.UiFare
import com.alancamargo.tubecalculator.fares.ui.model.UiFareOption
import com.alancamargo.tubecalculator.fares.ui.model.UiTicket
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketTime
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketType

internal fun UiStation.toDomain() = Station(
    id = id,
    name = name,
    modes = modes.map { it.toDomain() }
)

internal fun UiFare.toDomain() = when (this) {
    is UiFare.UiRailFare -> toDomain()
    is UiFare.UiBusAndTramFare -> toDomain()
}

private fun UiFare.UiRailFare.toDomain() = Fare.RailFare(
    header = header,
    fareOptions = fareOptions.map { it.toDomain() },
    messages = messages
)

private fun UiFare.UiBusAndTramFare.toDomain() = Fare.BusAndTramFare(cost = cost)

private fun UiFareOption.toDomain() = FareOption(
    label = label,
    origin = origin,
    destination = destination,
    description = description,
    passengerType = passengerType,
    tickets = tickets.map { it.toDomain() }
)

private fun UiTicket.toDomain() = Ticket(
    type = type.toDomain(),
    time = time.toDomain(),
    cost = cost
)

private fun UiTicketType.toDomain() = when (this) {
    UiTicketType.PAY_AS_YOU_GO -> TicketType.PAY_AS_YOU_GO
    UiTicketType.CASH -> TicketType.CASH
}

private fun UiTicketTime.toDomain() = TicketTime(
    label = label,
    description = description
)

private fun UiMode.toDomain() = when (this) {
    UiMode.DLR -> Mode.DLR
    UiMode.ELIZABETH_LINE -> Mode.ELIZABETH_LINE
    UiMode.NATIONAL_RAIL -> Mode.NATIONAL_RAIL
    UiMode.OVERGROUND -> Mode.OVERGROUND
    UiMode.UNDERGROUND -> Mode.UNDERGROUND
}
