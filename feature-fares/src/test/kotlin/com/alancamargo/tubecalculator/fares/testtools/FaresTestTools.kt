package com.alancamargo.tubecalculator.fares.testtools

import com.alancamargo.tubecalculator.common.domain.model.Mode
import com.alancamargo.tubecalculator.common.domain.model.Station
import com.alancamargo.tubecalculator.fares.data.mapping.toDomain
import com.alancamargo.tubecalculator.fares.data.model.database.DbRailFare
import com.alancamargo.tubecalculator.fares.data.model.responses.*
import com.alancamargo.tubecalculator.fares.domain.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal const val STATION_ID = "12345"
internal const val BUS_AND_TRAM_JOURNEY_COUNT = 2
internal const val BUS_AND_TRAM_FARE = "3.30"
internal const val CHEAPEST_TOTAL_FARE = "2.70"

private const val HEADER = "Single fare finder"

internal fun stubDbRailFare(): DbRailFare {
    val json = Json.encodeToString(listOf(stubRailFareResponse()))
    return DbRailFare(
        id = "$STATION_ID#$STATION_ID",
        originId = STATION_ID,
        destinationId = STATION_ID,
        jsonResponse = json
    )
}

internal fun stubRailFareResponse() = RailFareResponse(
    header = HEADER,
    fareOptions = stubFareResponseList(),
    messages = stubFareMessageResponseList()
)

internal fun stubRailFare() = stubRailFareResponse().toDomain()

internal fun stubBusAndTramFare() = Fare.BusAndTramFare(BUS_AND_TRAM_FARE)

internal fun stubRailFaresWithAlternativeRoute() = listOf(
    Fare.RailFare(
        header = "Single Fare Finder",
        fareOptions = listOf(
            FareOption(
                label = "Default route",
                origin = "Romford Rail Station",
                destination = "Camden Road Rail Station",
                description = "Default route",
                passengerType = "Adult",
                tickets = listOf(
                    Ticket(
                        type = TicketType.PAY_AS_YOU_GO,
                        time = TicketTime(
                            label = "Off Peak",
                            description = "At all other times including public holidays"
                        ),
                        cost = "3.50"
                    ),
                    Ticket(
                        type = TicketType.PAY_AS_YOU_GO,
                        time = TicketTime(
                            label = "Peak",
                            description = "Monday to Friday from 0630 to 0930 and from 1600 to 1900"
                        ),
                        cost = "5.50"
                    )
                )
            )
        ),
        messages = emptyList()
    ),
    Fare.RailFare(
        header = "Alternate Fares",
        fareOptions = listOf(
            FareOption(
                label = "Alternative route",
                origin = "Romford Rail Station",
                destination = "Camden Road Rail Station",
                description = "Avoiding Zone 1 via Stratford",
                passengerType = "Adult",
                tickets = listOf(
                    Ticket(
                        type = TicketType.PAY_AS_YOU_GO,
                        time = TicketTime(
                            label = "Off Peak",
                            description = "At all other times including public holidays"
                        ),
                        cost = "2.70"
                    ),
                    Ticket(
                        type = TicketType.PAY_AS_YOU_GO,
                        time = TicketTime(
                            label = "Peak",
                            description = "Monday to Friday from 0630 to 0930 and from 1600 to 1900"
                        ),
                        cost = "3.30"
                    )
                )
            )
        ),
        messages = emptyList()
    )
)

internal fun stubStation() = Station(
    id = STATION_ID,
    name = "Romford",
    modes = listOf(Mode.ELIZABETH_LINE, Mode.OVERGROUND, Mode.NATIONAL_RAIL)
)

private fun stubFareResponseList() = listOf(
    FareOptionResponse(
        label = "Default route",
        origin = "Romford",
        destination = "Camden Road",
        description = "Default route",
        passengerType = "Adult",
        tickets = stubTicketResponseList()
    )
)

private fun stubTicketResponseList() = listOf(
    TicketResponse(
        typeWrapper = stubTicketTypeResponse(),
        time = stubTicketTimeResponse(),
        cost = "3.50"
    )
)

private fun stubFareMessageResponseList() = listOf(
    FareMessageResponse(message = "Pay your fares or we will throw you in jail"),
    FareMessageResponse(message = "")
)

private fun stubTicketTypeResponse() = TicketTypeResponseWrapper(
    type = TicketTypeResponse.PAY_AS_YOU_GO
)

private fun stubTicketTimeResponse() = TicketTimeResponse(
    label = "Off Peak",
    description = "At all other times including public holidays."
)
