package com.alancamargo.tubecalculator.fares.data.mapping

import com.alancamargo.tubecalculator.fares.data.model.responses.FareOptionResponse
import com.alancamargo.tubecalculator.fares.data.model.responses.RailFareResponse
import com.alancamargo.tubecalculator.fares.data.model.responses.TicketResponse
import com.alancamargo.tubecalculator.fares.data.model.responses.TicketTimeResponse
import com.alancamargo.tubecalculator.fares.domain.model.FareOption
import com.alancamargo.tubecalculator.fares.domain.model.FareRoot
import com.alancamargo.tubecalculator.fares.domain.model.Ticket
import com.alancamargo.tubecalculator.fares.domain.model.TicketTime

internal fun RailFareResponse.toDomain() = FareRoot.RailFare(
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
    type = type.label,
    time = time.toDomain(),
    cost = cost
)

private fun TicketTimeResponse.toDomain() = TicketTime(
    label = label,
    description = description
)
