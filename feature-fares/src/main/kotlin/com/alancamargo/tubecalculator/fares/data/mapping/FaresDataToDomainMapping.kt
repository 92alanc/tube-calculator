package com.alancamargo.tubecalculator.fares.data.mapping

import com.alancamargo.tubecalculator.fares.data.model.responses.*
import com.alancamargo.tubecalculator.fares.domain.model.*

internal fun RailFareResponse.toDomain() = Fare.RailFare(
    header = header,
    fareOptions = fareOptions.map { it.toDomain() },
    messages = messages.map { it.message }
)

private fun FareOptionResponse.toDomain() = FareOption(
    label = label,
    origin = origin,
    destination = destination,
    description = description,
    passengerType = passengerType,
    tickets = tickets.map { it.toDomain() }
)

private fun TicketResponse.toDomain() = Ticket(
    type = typeWrapper.type.toDomain(),
    time = time.toDomain(),
    cost = cost
)

private fun TicketTimeResponse.toDomain() = TicketTime(
    label = label,
    description = description
)

private fun TicketTypeResponse.toDomain() = when (this) {
    TicketTypeResponse.PAY_AS_YOU_GO -> TicketType.PAY_AS_YOU_GO
    TicketTypeResponse.CASH -> TicketType.CASH
}
