package com.alancamargo.tubecalculator.fares.ui.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.model.UiFare
import com.alancamargo.tubecalculator.fares.ui.model.UiFareOption
import com.alancamargo.tubecalculator.fares.ui.model.UiTicket
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketTime
import com.alancamargo.tubecalculator.fares.ui.model.UiTicketType
import com.alancamargo.tubecalculator.core.design.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FaresScreen(
    adUnitId: String,
    adLoader: AdLoader,
    isLoading: Boolean,
    fares: List<UiFare>?,
    onMessageButtonClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { CustomFontText(text = stringResource(R.string.fares)) },
                colors = TopAppBarColors(
                    containerColor = colorResource(CoreR.color.white),
                    scrolledContainerColor = colorResource(CoreR.color.white),
                    navigationIconContentColor = colorResource(CoreR.color.black),
                    titleContentColor = colorResource(CoreR.color.black),
                    actionIconContentColor = colorResource(CoreR.color.black)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(CoreR.string.content_description_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .padding(
                    top = dimensionResource(CoreR.dimen.spacing_8),
                    start = dimensionResource(CoreR.dimen.spacing_8),
                    end = dimensionResource(CoreR.dimen.spacing_8)
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            when {
                isLoading -> {
                    // TODO: handle loading state
                }

                fares != null -> {
                    fares.forEach { fare ->
                        when (fare) {
                            is UiFare.UiRailFare -> RailFareItem(fare, onMessageButtonClicked)
                            is UiFare.UiBusAndTramFare -> BusAndTramFareItem(fare)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FaresScreenPreview() {
    FaresScreen(
        adUnitId = "",
        adLoader = object : AdLoader {
            override fun loadBannerAds(target: View) {}

            override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {}
        },
        isLoading = false,
        fares = listOf(
            UiFare.UiRailFare(
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
                                cost = "£3.80"
                            ),
                            UiTicket(
                                type = UiTicketType.PAY_AS_YOU_GO,
                                time = UiTicketTime(
                                    label = "Peak",
                                    description = "Monday to Friday from 0630 to 0930 and from 1600 to 1900."
                                ),
                                cost = "£5.80"
                            )
                        )
                    )
                ),
                messages = listOf(
                    "Message 1",
                    "Message 2"
                )
            ),
            UiFare.UiRailFare(
                header = "Alternate Fares",
                fareOptions = listOf(
                    UiFareOption(
                        label = "Alternative Route",
                        description = "Avoiding Zone 1 via Stratford",
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
                                cost = "£3.00"
                            ),
                            UiTicket(
                                type = UiTicketType.PAY_AS_YOU_GO,
                                time = UiTicketTime(
                                    label = "Peak",
                                    description = "Monday to Friday from 0630 to 0930 and from 1600 to 1900."
                                ),
                                cost = "£3.60"
                            )
                        )
                    )
                ),
                messages = listOf(
                    "Message 1",
                    "Message 2"
                )
            ),
            UiFare.UiBusAndTramFare(cost = "£1.75")
        ),
        onMessageButtonClicked = {},
        onBackClicked = {}
    )
}
