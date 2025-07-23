package com.alancamargo.tubecalculator.fares.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.model.UiFare
import com.alancamargo.tubecalculator.core.design.R as CoreR

@Composable
internal fun BusAndTramFareItem(fare: UiFare.UiBusAndTramFare) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(CoreR.color.white)),
        shape = RoundedCornerShape(size = dimensionResource(CoreR.dimen.spacing_8)),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(CoreR.color.grey_dark)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(CoreR.dimen.spacing_16)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(CoreR.dimen.spacing_8),
                    Alignment.CenterHorizontally
                )
            ) {
                Image(painter = painterResource(CoreR.drawable.ic_bus), contentDescription = null)
                CustomFontText(
                    text = stringResource(R.string.fares_bus_and_tram_fares),
                    textStyle = TextStyle.HEADLINE_2
                )
                Image(painter = painterResource(CoreR.drawable.ic_tram), contentDescription = null)
            }

            CustomFontText(text = fare.cost, textStyle = TextStyle.HEADLINE_1)
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun BusAndTramFareItemPreview() {
    BusAndTramFareItem(fare = UiFare.UiBusAndTramFare(cost = "£1.65"))
}
