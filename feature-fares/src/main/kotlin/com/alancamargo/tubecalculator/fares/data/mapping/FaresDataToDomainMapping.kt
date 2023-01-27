package com.alancamargo.tubecalculator.fares.data.mapping

import com.alancamargo.tubecalculator.fares.data.model.responses.FareResponse
import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
import com.alancamargo.tubecalculator.fares.data.model.responses.TicketResponse
import com.alancamargo.tubecalculator.fares.data.model.responses.TicketTimeResponse
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.FareRoot
import com.alancamargo.tubecalculator.fares.domain.model.Ticket
import com.alancamargo.tubecalculator.fares.domain.model.TicketTime

internal fun RailFareResponse.toDomain() = FareRoot.RailFare(
    header = header,
    fares = fares.map { it.toDomain() },
    messages = messages.map { it.message }
)

private fun FareResponse.toDomain() = Fare(
    label = label,
    origin = origin,
    destination = destination,
    description = description,
    passengerType = passengerType,
    tickets = tickets.map { it.toDomain() }
)

private fun TicketResponse.toDomain() = Ticket(
    type = type.label,
    time = time.toDomain(),
    cost = cost
)

private fun TicketTimeResponse.toDomain() = TicketTime(
    label = label,
    description = description
)
