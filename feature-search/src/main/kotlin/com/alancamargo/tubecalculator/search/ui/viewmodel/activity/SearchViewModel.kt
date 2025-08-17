package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val analytics: SearchAnalytics,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _action = MutableSharedFlow<SearchViewAction>()

    val action: SharedFlow<SearchViewAction> = _action

    fun onCreate() {
        analytics.trackScreenViewed()
    }

    fun onBackPressed() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.Finish)
        }
    }

    fun onNextClicked(journey: Journey) {
        viewModelScope.launch(dispatcher) {
            when (journey) {
                is Journey.Rail -> {
                    if (journey.origin != journey.destination) {
                        val journey = Journey.Rail(
                            origin = journey.origin,
                            destination = journey.destination
                        )
                        _action.emit(SearchViewAction.SendJourney(journey))
                    } else {
                        val error = UiSearchError.SAME_ORIGIN_AND_DESTINATION
                        _action.emit(SearchViewAction.ShowErrorDialogue(error))
                    }
                }

                is Journey.BusAndTram -> {
                    val journey = Journey.BusAndTram(journeyCount = journey.journeyCount)
                    _action.emit(SearchViewAction.SendJourney(journey))
                }
            }
        }
    }
}
