package com.alancamargo.tubecalculator.fares.data.mapping

import com.alancamargo.tubecalculator.fares.data.model.responses.*
import com.alancamargo.tubecalculator.fares.domain.model.*

internal fun Fare.RailFare.toData() = RailFareResponse(
    header = header,
    fareOptions = fareOptions.map { it.toData() },
    messages = messages.map(::FareMessageResponse)
)

private fun FareOption.toData() = FareOptionResponse(
    label = label,
    origin = origin,
    destination = destination,
    description = description,
    passengerType = passengerType,
    tickets = tickets.map { it.toData() }
)

private fun Ticket.toData() = TicketResponse(
    typeWrapper = TicketTypeResponseWrapper(type.toData()),
    time = time.toData(),
    cost = cost
)

private fun TicketTime.toData() = TicketTimeResponse(
    label = label,
    description = description
)

private fun TicketType.toData() = when (this) {
    TicketType.PAY_AS_YOU_GO -> TicketTypeResponse.PAY_AS_YOU_GO
    TicketType.CASH -> TicketTypeResponse.CASH
}
