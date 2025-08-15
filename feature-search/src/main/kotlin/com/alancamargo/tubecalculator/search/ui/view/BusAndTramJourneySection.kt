package com.alancamargo.tubecalculator.search.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.model.BusAndTramJourneySectionData
import com.alancamargo.tubecalculator.core.design.R as CoreR

@Composable
internal fun BusAndTramJourneySection(data: BusAndTramJourneySectionData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(CoreR.dimen.spacing_16)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreR.dimen.spacing_8)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            Image(
                painterResource(CoreR.drawable.ic_bus),
                contentDescription = null
            )
            CustomFontText(text = stringResource(R.string.search_how_many_bus_tram_journeys))
            Image(
                painterResource(CoreR.drawable.ic_tram),
                contentDescription = null
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            CustomFontText(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.search_bus_tram_journeys_info_short),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = data.onMoreInformationClicked) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.more_information)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .padding(dimensionResource(CoreR.dimen.spacing_16))
                    .border(
                        width = 1.dp,
                        color = colorResource(CoreR.color.black),
                        shape = RoundedCornerShape(
                            corner = CornerSize(
                                dimensionResource(CoreR.dimen.spacing_8)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CustomFontText(
                    text = data.count.toString(),
                    textStyle = TextStyle.HEADLINE_1
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_16)
            )) {
                IconButton(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = colorResource(CoreR.color.black),
                        shape = CircleShape
                    ),
                    onClick = data.onCountIncreased
                ) {
                   Icon(
                       painter = painterResource(CoreR.drawable.ic_arrow_up),
                       contentDescription = stringResource(
                           R.string.search_content_description_more
                       )
                   )
                }

                IconButton(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = colorResource(CoreR.color.black),
                        shape = CircleShape
                    ),
                    onClick = data.onCountDecreased
                ) {
                    Icon(
                        painter = painterResource(CoreR.drawable.ic_arrow_down),
                        contentDescription = stringResource(
                            R.string.search_content_description_less
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BusAndTramJourneySectionPreview() {
    BusAndTramJourneySection(
        data = BusAndTramJourneySectionData(
            count = 0,
            onCountIncreased = {},
            onCountDecreased = {},
            onMoreInformationClicked = {}
        )
    )
}
