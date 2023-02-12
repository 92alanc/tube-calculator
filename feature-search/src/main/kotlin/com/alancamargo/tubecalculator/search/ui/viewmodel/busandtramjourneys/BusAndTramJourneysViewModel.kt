package com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BusAndTramJourneysViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(BusAndTramJourneysViewState())
    private val _action = MutableSharedFlow<BusAndTramJourneysViewAction>()

    val state: StateFlow<BusAndTramJourneysViewState> = _state
    val action: SharedFlow<BusAndTramJourneysViewAction> = _action

    var busAndTramJourneyCount = 0
        private set

    fun onCreate(journeyCount: Int) {
        busAndTramJourneyCount = journeyCount
        updateBusAndTramJourneyCount()
    }

    fun increaseBusAndTramJourneyCount() {
        busAndTramJourneyCount = ++busAndTramJourneyCount
        updateBusAndTramJourneyCount()
    }

    fun decreaseBusAndTramJourneyCount() {
        busAndTramJourneyCount = --busAndTramJourneyCount

        if (busAndTramJourneyCount < 0) {
            busAndTramJourneyCount++
        } else {
            updateBusAndTramJourneyCount()
        }
    }

    fun onMoreInfoClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(BusAndTramJourneysViewAction.ShowMoreInfo)
        }
    }

    private fun updateBusAndTramJourneyCount() {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.onUpdateBusAndTramJourneyCount(busAndTramJourneyCount)
            }
        }
    }
}
