package com.alancamargo.tubecalculator.fares.ui.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.ComposableAdView
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.core.design.view.ShimmerBox
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
    cheapestTotalFare: String,
    onMessageButtonClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNewSearchClicked: () -> Unit
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
                            contentDescription = stringResource(
                                CoreR.string.content_description_back
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).fillMaxSize()) {
            when {
                isLoading -> LoadingShimmer()

                fares != null -> FaresContent(
                    fares,
                    cheapestTotalFare,
                    onMessageButtonClicked,
                    onNewSearchClicked
                )
            }

            Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_16)))

            ComposableAdView(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                adUnitId = adUnitId,
                adLoader = adLoader
            )
        }
    }
}

@Composable
private fun LoadingShimmer() {
    Column(
        Modifier.padding(dimensionResource(CoreR.dimen.spacing_24))
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.88f)
    ) {
        ShimmerBox(
            Modifier.width(240.dp)
                .height(30.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_16)))
        ShimmerBox(Modifier.width(140.dp).height(24.dp))
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        ShimmerBox(Modifier.width(90.dp).height(24.dp))
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        ShimmerBox(Modifier.width(140.dp).height(24.dp))
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        ShimmerBox(Modifier.width(140.dp).height(24.dp))
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        ShimmerBox(Modifier.width(200.dp).height(24.dp))
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_24)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(120.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(60.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(260.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(60.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_24)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(120.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(60.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(260.dp).height(24.dp))
        }
        Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_4)))
        Row {
            Spacer(Modifier.width(dimensionResource(CoreR.dimen.spacing_16)))
            ShimmerBox(Modifier.width(60.dp).height(24.dp))
        }
    }
}

@Composable
private fun FaresContent(
    fares: List<UiFare>,
    cheapestTotalFare: String,
    onMessageButtonClicked: () -> Unit,
    onNewSearchClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreR.dimen.spacing_16)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(fraction = 0.7f).padding(
                top = dimensionResource(CoreR.dimen.spacing_8),
                start = dimensionResource(CoreR.dimen.spacing_8),
                end = dimensionResource(CoreR.dimen.spacing_8)
            ).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(CoreR.dimen.spacing_8)
            )
        ) {
            fares.forEach { fare ->
                when (fare) {
                    is UiFare.UiRailFare -> RailFareItem(
                        fare,
                        onMessageButtonClicked
                    )
                    is UiFare.UiBusAndTramFare -> BusAndTramFareItem(fare)
                }
            }
        }

        HorizontalDivider()

        CustomFontText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(
                R.string.fares_cheapest_total_fare_format,
                cheapestTotalFare
            ),
            textStyle = TextStyle.HEADLINE_1
        )

        Button(
            modifier = Modifier.fillMaxWidth(fraction = 0.9f)
                .align(Alignment.CenterHorizontally),
            onClick = onNewSearchClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(CoreR.color.red),
                contentColor = colorResource(CoreR.color.white)
            )
        ) {
            CustomFontText(
                text = stringResource(R.string.fares_new_search),
                textStyle = TextStyle.BODY_WHITE
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FaresScreenLoadingPreview() {
    FaresScreen(
        adUnitId = "",
        adLoader = object : AdLoader {
            override fun loadBannerAds(target: View) {}

            override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {}
        },
        isLoading = true,
        fares = null,
        cheapestTotalFare = "2.70",
        onMessageButtonClicked = {},
        onBackClicked = {},
        onNewSearchClicked = {}
    )
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
        cheapestTotalFare = "2.70",
        onMessageButtonClicked = {},
        onBackClicked = {},
        onNewSearchClicked = {}
    )
}
