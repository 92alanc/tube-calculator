package com.alancamargo.tubecalculator.search.ui.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.view.ComposableAdView
import com.alancamargo.tubecalculator.core.design.view.CustomScaffold
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.model.BusAndTramJourneySectionData
import com.alancamargo.tubecalculator.search.ui.model.RailJourneySectionData
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.core.design.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    journeyType: JourneyType,
    onOriginQueryChanged: (String) -> Unit,
    originSearchResults: List<UiStation>?,
    onOriginSelected: (UiStation) -> Unit,
    selectedOrigin: UiStation?,
    onDestinationQueryChanged: (String) -> Unit,
    destinationSearchResults: List<UiStation>?,
    onDestinationSelected: (UiStation) -> Unit,
    selectedDestination: UiStation?,
    onBusAndTramJourneyCountIncreased: () -> Unit,
    onBusAndTramJourneyCountDecreased: () -> Unit,
    busAndTramJourneyCount: Int,
    onBusAndTramJourneyMoreInformationClicked: () -> Unit,
    adUnitId: String,
    adLoader: AdLoader,
    onNextClicked: (Journey) -> Unit,
    onBackClicked: () -> Unit
) {
    CustomScaffold(
        titleRes = R.string.search,
        onBackClicked = onBackClicked
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = colorResource(CoreR.color.white))
        ) {
            var isNextButtonVisible = false

            Column(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(CoreR.dimen.spacing_16),
                        start = dimensionResource(CoreR.dimen.spacing_16),
                        end = dimensionResource(CoreR.dimen.spacing_16)
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.78f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(CoreR.dimen.spacing_8)
                )
            ) {
                when (journeyType) {
                    JourneyType.RAIL -> {
                        isNextButtonVisible = selectedOrigin != null && selectedDestination != null
                        val originData = RailJourneySectionData(
                            textFieldState = TextFieldState(
                                initialText = selectedOrigin?.name.orEmpty()
                            ),
                            searchType = SearchType.ORIGIN,
                            searchResults = originSearchResults,
                            selectedStation = selectedOrigin,
                            onQueryChanged = onOriginQueryChanged,
                            onStationSelected = onOriginSelected
                        )
                        RailJourneySection(originData)

                        val destinationData = RailJourneySectionData(
                            textFieldState = TextFieldState(
                                initialText = selectedDestination?.name.orEmpty()
                            ),
                            searchType = SearchType.DESTINATION,
                            searchResults = destinationSearchResults,
                            selectedStation = selectedDestination,
                            onQueryChanged = onDestinationQueryChanged,
                            onStationSelected = onDestinationSelected
                        )
                        RailJourneySection(destinationData)
                    }

                    JourneyType.BUS_AND_TRAM -> {
                        isNextButtonVisible = busAndTramJourneyCount > 0
                        
                        val busAndTramData = BusAndTramJourneySectionData(
                            count = busAndTramJourneyCount,
                            onCountIncreased = onBusAndTramJourneyCountIncreased,
                            onCountDecreased = onBusAndTramJourneyCountDecreased,
                            onMoreInformationClicked = onBusAndTramJourneyMoreInformationClicked
                        )
                        BusAndTramJourneySection(busAndTramData)
                    }
                }
            }

            if (isNextButtonVisible) {
                Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_16)))

                Box(
                    Modifier.align(Alignment.End)
                        .padding(end = dimensionResource(CoreR.dimen.spacing_16))
                ) {
                    FloatingActionButton(
                        containerColor = colorResource(CoreR.color.red),
                        contentColor = colorResource(CoreR.color.white),
                        onClick = {
                            val journey = when (journeyType) {
                                JourneyType.RAIL -> Journey.Rail(
                                    origin = selectedOrigin!!,
                                    destination = selectedDestination!!
                                )

                                JourneyType.BUS_AND_TRAM -> Journey.BusAndTram(
                                    journeyCount = busAndTramJourneyCount
                                )
                            }

                            onNextClicked(journey)
                        }
                    ) {
                        Icon(
                            painter = painterResource(CoreR.drawable.ic_next),
                            contentDescription = stringResource(CoreR.string.next)
                        )
                    }
                }
            }

            Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_24)))

            ComposableAdView(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                adUnitId = adUnitId,
                adLoader = adLoader
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenRailPreview() {
    SearchScreen(
        journeyType = JourneyType.RAIL,
        onOriginQueryChanged = {},
        originSearchResults = null,
        onOriginSelected = {},
        selectedOrigin = null,
        onDestinationQueryChanged = {},
        destinationSearchResults = null,
        onDestinationSelected = {},
        selectedDestination = null,
        onBusAndTramJourneyCountIncreased = {},
        onBusAndTramJourneyCountDecreased = {},
        busAndTramJourneyCount = 0,
        onBusAndTramJourneyMoreInformationClicked = {},
        adUnitId = "",
        adLoader = object : AdLoader {
            override fun loadBannerAds(target: View) {}

            override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {}
        },
        onNextClicked = {},
        onBackClicked = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenBusAndTramPreview() {
    SearchScreen(
        journeyType = JourneyType.BUS_AND_TRAM,
        onOriginQueryChanged = {},
        originSearchResults = null,
        onOriginSelected = {},
        selectedOrigin = null,
        onDestinationQueryChanged = {},
        destinationSearchResults = null,
        onDestinationSelected = {},
        selectedDestination = null,
        onBusAndTramJourneyCountIncreased = {},
        onBusAndTramJourneyCountDecreased = {},
        busAndTramJourneyCount = 2,
        onBusAndTramJourneyMoreInformationClicked = {},
        adUnitId = "",
        adLoader = object : AdLoader {
            override fun loadBannerAds(target: View) {}

            override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {}
        },
        onNextClicked = {},
        onBackClicked = {}
    )
}
