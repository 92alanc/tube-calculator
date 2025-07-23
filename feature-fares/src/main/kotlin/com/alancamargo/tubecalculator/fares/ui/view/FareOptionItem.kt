package com.alancamargo.tubecalculator.fares.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.model.UiFareOption
import com.alancamargo.tubecalculator.fares.ui.model.UiTicket
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketTime
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketType
import com.alancamargo.tubecalculator.core.design.R as CoreR

@Composable
internal fun FareOptionItem(fareOption: UiFareOption) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(CoreR.dimen.spacing_8)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreR.dimen.spacing_8)
        )
    ) {
        CustomFontText(text = fareOption.label, textStyle = TextStyle.HEADLINE_1)
        CustomFontText(text = fareOption.description, textStyle = TextStyle.CAPTION)
        CustomFontText(
            text = stringResource(
                R.string.fares_from_format,
                fareOption.origin
            )
        )
        CustomFontText(
            text = stringResource(
                R.string.fares_to_format,
                fareOption.destination
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            items(fareOption.tickets.size) { ticketIndex ->
                val ticket = fareOption.tickets[ticketIndex]
                TicketItem(ticket)
            }
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun FareOptionItemPreview() {
    FareOptionItem(
        fareOption = UiFareOption(
            label = "Default Route",
            description = "Default Route",
            origin = "Romford",
            destination = "Camden Road",
            passengerType = "Adult",
            tickets = listOf(
                UiTicket(
                    type = UiTicketType.PAY_AS_YOU_GO,
                    time = UiTicketTime(
                        label = "Off Peak",
                        description = "At all other times including public holidays."
                    ),
                    cost = "£3.50"
                ),
                UiTicket(
                    type = UiTicketType.PAY_AS_YOU_GO,
                    time = UiTicketTime(
                        label = "Peak",
                        description = "Monday to Friday from 0630 to 0930 and from 1600 to 1900."
                    ),
                    cost = "£5.50"
                )
            )
        )
    )
}
