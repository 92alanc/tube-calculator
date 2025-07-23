package com.alancamargo.tubecalculator.fares.ui.mapping

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

internal fun Fare.BusAndTramFare.toUi() = UiFare.UiBusAndTramFare(cost = cost)

internal fun Fare.RailFare.toUi() = UiFare.UiRailFare(
    header = header,
    fareOptions = fareOptions.map { it.toUi() },
    messages = messages
)

private fun FareOption.toUi() = UiFareOption(
    label = label,
    origin = origin,
    destination = destination,
    description = description,
    passengerType = passengerType,
    tickets = tickets.map { it.toUi() }
)

private fun Ticket.toUi() = UiTicket(
    type = type.toUi(),
    time = time.toUi(),
    cost = cost
)

private fun TicketType.toUi() = when (this) {
    TicketType.PAY_AS_YOU_GO -> UiTicketType.PAY_AS_YOU_GO
    TicketType.CASH -> UiTicketType.CASH
}

private fun TicketTime.toUi() = UiTicketTime(
    label = label,
    description = description
)
