package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.di.UiDelay
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val analytics: SearchAnalytics,
    private val logger: Logger,
    @UiDelay private val uiDelay: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(SearchViewState())
    private val _action = MutableSharedFlow<SearchViewAction>()

    private var origin: UiStation? = null
    private var destination: UiStation? = null
    private var busAndTramJourneyCount = 0

    val state: StateFlow<SearchViewState> = _state
    val action: SharedFlow<SearchViewAction> = _action

    fun onCreate(isFirstLaunch: Boolean, journey: Journey?, journeyType: JourneyType) {
        if (!isFirstLaunch) {
            return
        }

        analytics.trackScreenViewed()

        if (journey != null) {
            _state.update { it.showNextButton() }
        }

        viewModelScope.launch(dispatcher) {
            delay(uiDelay)
            val action = getActionForJourney(journey, journeyType)
            _action.emit(action)
        }
    }

    fun onOriginSelected(origin: UiStation?) {
        this.origin = origin

        if (origin != null && destination != null) {
            _state.update { it.showNextButton() }
        } else {
            _state.update { it.hideNextButton() }
        }
    }

    fun onDestinationSelected(destination: UiStation?) {
        this.destination = destination

        if (origin != null && destination != null) {
            _state.update { it.showNextButton() }
        } else {
            _state.update { it.hideNextButton() }
        }
    }

    fun onBusAndTramJourneyCountSelected(count: Int) {
        busAndTramJourneyCount = count

        if (count > 0) {
            _state.update { it.showNextButton() }
        } else {
            _state.update { it.hideNextButton() }
        }
    }

    fun onBackPressed() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.Finish)
        }
    }

    fun onNextClicked() {
        val (origin, destination) = this.origin to this.destination

        viewModelScope.launch(dispatcher) {
            if (origin != null && destination != null) {
                if (origin != destination) {
                    val journey = Journey.Rail(origin, destination)
                    _action.emit(SearchViewAction.SendJourney(journey))
                } else {
                    val error = UiSearchError.SAME_ORIGIN_AND_DESTINATION
                    _action.emit(SearchViewAction.ShowErrorDialogue(error))
                }
            } else if (busAndTramJourneyCount > 0) {
                val journey = Journey.BusAndTram(busAndTramJourneyCount)
                _action.emit(SearchViewAction.SendJourney(journey))
            } else {
                logger.debug(
                    "Origin: $origin, destination: $destination, Bus and tram journey count: $busAndTramJourneyCount"
                )
                val error = UiSearchError.GENERIC
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }
        }
    }

    private fun getActionForJourney(
        journey: Journey?,
        journeyType: JourneyType
    ) = journey?.let {
        when (it) {
            is Journey.Rail -> {
                SearchViewAction.AttachPreFilledRailJourneyFragments(it)
            }

            is Journey.BusAndTram -> {
                SearchViewAction.AttachPreFilledBusAndTramJourneyFragment(it)
            }
        }
    } ?: run {
        when (journeyType) {
            JourneyType.RAIL -> {
                SearchViewAction.AttachBlankRailJourneyFragments
            }

            JourneyType.BUS_AND_TRAM -> {
                SearchViewAction.AttachBlankBusAndTramJourneyFragment
            }
        }
    }
}
