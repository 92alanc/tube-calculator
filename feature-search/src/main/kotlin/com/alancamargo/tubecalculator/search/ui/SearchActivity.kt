package com.alancamargo.tubecalculator.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.alancamargo.tubecalculator.search.ui.view.SearchScreen
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewModel
import com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys.BusAndTramJourneysViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys.BusAndTramJourneysViewModel
import com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch.StationSearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch.StationSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import com.alancamargo.tubecalculator.core.design.R as R2

@AndroidEntryPoint
internal class SearchActivity : AppCompatActivity() {

    private val args by args<Args>()
    private val viewModel by viewModels<SearchViewModel>()
    private val originViewModel by viewModels<StationSearchViewModel>()
    private val destinationViewModel by viewModels<StationSearchViewModel>()
    private val busAndTramViewModel by viewModels<BusAndTramJourneysViewModel>()

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    @Inject
    lateinit var adLoader: AdLoader

    private val originSearchResultsState = mutableStateOf<List<UiStation>?>(null)
    private val selectedOriginState = mutableStateOf<UiStation?>(null)
    private val destinationSearchResultsState = mutableStateOf<List<UiStation>?>(null)
    private val selectedDestinationState = mutableStateOf<UiStation?>(null)
    private val busAndTramJourneyCountState = mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen(
                journeyType = args.journeyType,
                onOriginQueryChanged = originViewModel::onQueryChanged,
                originSearchResults = originSearchResultsState.value,
                onOriginSelected = originViewModel::onStationSelected,
                selectedOrigin = selectedOriginState.value,
                onDestinationQueryChanged = destinationViewModel::onQueryChanged,
                destinationSearchResults = destinationSearchResultsState.value,
                onDestinationSelected = destinationViewModel::onStationSelected,
                selectedDestination = selectedDestinationState.value,
                onBusAndTramJourneyCountIncreased = busAndTramViewModel::increaseBusAndTramJourneyCount,
                onBusAndTramJourneyCountDecreased = busAndTramViewModel::decreaseBusAndTramJourneyCount,
                busAndTramJourneyCount = busAndTramJourneyCountState.intValue,
                onBusAndTramJourneyMoreInformationClicked = busAndTramViewModel::onMoreInfoClicked,
                adUnitId = stringResource(R.string.ads_banner_search),
                adLoader = adLoader,
                onNextClicked = viewModel::onNextClicked,
                onBackClicked = viewModel::onBackPressed
            )
        }

        observeFlows()
        initialiseViewModels()
    }

    private fun observeFlows() {
        observeViewModelFlow(viewModel.action, ::handleAction)
        observeOriginFlows()
        observeDestinationFlows()
        observeBusAndTramFlows()
    }

    private fun initialiseViewModels() {
        viewModel.onCreate()

        when (args.journeyType) {
            JourneyType.RAIL -> {
                val railJourney = args.journey as? Journey.Rail
                originViewModel.onCreate(station = railJourney?.origin)
                destinationViewModel.onCreate(station = railJourney?.destination)
            }

            JourneyType.BUS_AND_TRAM -> {
                val busAndTramJourney = args.journey as? Journey.BusAndTram
                busAndTramViewModel.onCreate(journeyCount = busAndTramJourney?.journeyCount ?: 0)
            }
        }
    }

    private fun observeOriginFlows() {
        observeViewModelFlow(originViewModel.state) { state ->
            with (state) {
                originSearchResultsState.value = stations
                selectedOriginState.value = selectedStation
            }
        }
        observeViewModelFlow(originViewModel.action) { action ->
            when (action) {
                is StationSearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
            }
        }
    }

    private fun observeDestinationFlows() {
        observeViewModelFlow(destinationViewModel.state) { state ->
            with (state) {
                destinationSearchResultsState.value = stations
                selectedDestinationState.value = selectedStation
            }
        }
        observeViewModelFlow(destinationViewModel.action) { action ->
            when (action) {
                is StationSearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
            }
        }
    }

    private fun observeBusAndTramFlows() {
        observeViewModelFlow(busAndTramViewModel.state) { state ->
            busAndTramJourneyCountState.intValue = state.busAndTramJourneyCount
        }
        observeViewModelFlow(busAndTramViewModel.action) { action ->
            when (action) {
                is BusAndTramJourneysViewAction.ShowMoreInfo -> showMoreInfo()
            }
        }
    }

    private fun handleAction(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)

            is SearchViewAction.Finish -> finish()

            is SearchViewAction.SendJourney -> sendJourney(action.journey)
        }
    }

    private fun showErrorDialogue(error: UiSearchError) {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R2.string.error,
            messageRes = error.messageRes
        )
    }

    private fun showMoreInfo() {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R.string.bus_tram_journeys,
            messageRes = R.string.search_bus_tram_journeys_info
        )
    }

    private fun sendJourney(journey: Journey) {
        val data = Intent().putArguments(journey)
        setResult(RESULT_OK, data)
        finish()
    }

    @Parcelize
    data class Args(
        val journey: Journey?,
        val journeyType: JourneyType
    ) : Parcelable

    companion object {

        fun getIntent(context: Context, journey: Journey): Intent {
            val journeyType = if (journey is Journey.Rail) {
                JourneyType.RAIL
            } else {
                JourneyType.BUS_AND_TRAM
            }

            val args = Args(journey, journeyType)
            return context.createIntent(SearchActivity::class).putArguments(args)
        }

        fun getIntent(context: Context, journeyType: JourneyType): Intent {
            val args = Args(journey = null, journeyType)
            return context.createIntent(SearchActivity::class).putArguments(args)
        }
    }
}
