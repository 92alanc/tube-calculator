package com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BusAndTramJourneysViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(BusAndTramJourneysViewState())

    val state: StateFlow<BusAndTramJourneysViewState> = _state

    var busAndTramJourneyCount = 0
        private set

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

    private fun updateBusAndTramJourneyCount() {
        viewModelScope.launch(dispatcher) {
            _state.update {
                it.onUpdateBusAndTramJourneyCount(busAndTramJourneyCount)
            }
        }
    }
}
