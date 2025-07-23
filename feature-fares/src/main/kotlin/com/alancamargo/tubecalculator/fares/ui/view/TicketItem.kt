package com.alancamargo.tubecalculator.fares.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alancamargo.tubecalculator.core.design.R
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.fares.ui.model.UiTicket
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketTime
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketType

@Composable
internal fun TicketItem(ticket: UiTicket) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white)),
        shape = RoundedCornerShape(size = dimensionResource(R.dimen.spacing_8)),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(R.color.grey)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.spacing_8)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.spacing_8)
            )
        ) {
            CustomFontText(
                text = stringResource(ticket.type.labelRes),
                textStyle = TextStyle.HEADLINE_2
            )
            CustomFontText(text = ticket.time.label)
            CustomFontText(text = ticket.time.description, textStyle = TextStyle.CAPTION)
            CustomFontText(text = ticket.cost, textStyle = TextStyle.HEADLINE_1)
        }
    }
}

@Preview
@Composable
private fun TicketItemPreview() {
    TicketItem(
        ticket = UiTicket(
            type = UiTicketType.PAY_AS_YOU_GO,
            time = UiTicketTime(
                label = "Off Peak",
                description = "At all other times including public holidays."
            ),
            cost = "£3.50"
        )
    )
}
