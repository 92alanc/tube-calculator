package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.di.UiDelay
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val analytics: SearchAnalytics,
    @UiDelay private val uiDelay: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _action = MutableSharedFlow<SearchViewAction>()

    val action: SharedFlow<SearchViewAction> = _action

    fun onCreate(isFirstLaunch: Boolean, journey: Journey?, journeyType: JourneyType) {
        if (!isFirstLaunch) {
            return
        }

        analytics.trackScreenViewed()

        viewModelScope.launch(dispatcher) {
            delay(uiDelay)
            val action = getActionForJourney(journey, journeyType)
            _action.emit(action)
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
