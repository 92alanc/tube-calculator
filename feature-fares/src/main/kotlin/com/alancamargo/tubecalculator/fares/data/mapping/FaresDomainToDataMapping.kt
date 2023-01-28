package com.alancamargo.tubecalculator.fares.data.mapping

import com.alancamargo.tubecalculator.fares.data.model.responses.*
import com.alancamargo.tubecalculator.fares.domain.model.FareOption
import com.alancamargo.tubecalculator.fares.domain.model.FareRoot
import com.alancamargo.tubecalculator.fares.domain.model.Ticket
import com.alancamargo.tubecalculator.fares.domain.model.TicketTime

internal fun FareRoot.RailFare.toData() = RailFareResponse(
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
    type = TicketTypeResponse(type),
    time = time.toData(),
    cost = cost
)

private fun TicketTime.toData() = TicketTimeResponse(
    label = label,
    description = description
)
