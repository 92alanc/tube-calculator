package com.alancamargo.tubecalculator.fares.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.CustomCard
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.model.UiFare
import com.alancamargo.tubecalculator.fares.ui.model.UiFareOption
import com.alancamargo.tubecalculator.fares.ui.model.UiTicket
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketTime
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketType
import com.alancamargo.tubecalculator.core.design.R as CoreR

@Composable
internal fun RailFareItem(
    fare: UiFare.UiRailFare,
    onMessageButtonClicked: () -> Unit
) {
    CustomCard {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(CoreR.dimen.spacing_16)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = dimensionResource(CoreR.dimen.spacing_8),
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                CustomFontText(text = fare.header, textStyle = TextStyle.HEADLINE_1)

                if (fare.messages.isNotEmpty()) {
                    IconButton(onClick = onMessageButtonClicked) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.fares_messages)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(CoreR.dimen.spacing_8)
                )
            ) {
                items(fare.fareOptions.size) { fareOptionIndex ->
                    val fareOption = fare.fareOptions[fareOptionIndex]
                    RailFareOptionItem(fareOption)
                }
            }
        }
    }
}

@Preview
@Composable
private fun RailFareListPreview() {
    RailFareItem(
        fare = UiFare.UiRailFare(
            header = "Single Fare Finder",
            fareOptions = listOf(
                UiFareOption(
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
            ),
            messages = listOf(
                "Message 1",
                "Message 2"
            )
        ),
        onMessageButtonClicked = {}
    )
}
