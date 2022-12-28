package com.alancamargo.tubecalculator.fares.data.mapping

import com.alancamargo.tubecalculator.fares.data.model.FareListRootResponse
import com.alancamargo.tubecalculator.fares.data.model.FareResponse
import com.alancamargo.tubecalculator.fares.data.model.TicketResponse
import com.alancamargo.tubecalculator.fares.data.model.TicketTimeResponse
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot
import com.alancamargo.tubecalculator.fares.domain.model.Ticket
import com.alancamargo.tubecalculator.fares.domain.model.TicketTime

internal fun FareListRootResponse.toDomain() = FareListRoot(
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
