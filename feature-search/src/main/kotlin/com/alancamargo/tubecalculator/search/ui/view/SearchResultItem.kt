package com.alancamargo.tubecalculator.search.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.core.design.R as CoreR

@Composable
internal fun SearchResultItem(station: UiStation, onItemSelected: (UiStation) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(CoreR.dimen.spacing_8))
            .clickable { onItemSelected(station) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreR.dimen.spacing_8)
        )
    ) {
        val maxColumnCount = 3
        val rows = station.modes.chunked(maxColumnCount)

        if (rows.size > 1) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(CoreR.dimen.spacing_4)
                )
            ) {
                rows.forEach { modes ->
                    ModeRow(modes)
                }
            }
        } else {
            val modes = rows.first()
            ModeRow(modes)
        }

        CustomFontText(text = station.name)
    }
}

@Composable
private fun ModeRow(modes: List<UiMode>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreR.dimen.spacing_4)
        )
    ) {
        modes.forEach { mode ->
            Image(
                painter = painterResource(mode.iconRes),
                contentDescription = stringResource(mode.contentDescriptionRes)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultItemPreview() {
    SearchResultItem(
        station = UiStation(
            id = "12345",
            name = "The busiest station in London",
            modes = listOf(
                UiMode.UNDERGROUND,
                UiMode.DLR,
                UiMode.ELIZABETH_LINE,
                UiMode.OVERGROUND,
                UiMode.NATIONAL_RAIL
            )
        ),
        onItemSelected = {}
    )
}
