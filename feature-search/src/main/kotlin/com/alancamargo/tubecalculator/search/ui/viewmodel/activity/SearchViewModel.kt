package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(SearchViewState())
    private val _action = MutableSharedFlow<SearchViewAction>()

    private var origin: UiStation? = null
    private var destination: UiStation? = null
    private var busAndTramJourneyCount = 0

    val state: StateFlow<SearchViewState> = _state
    val action: SharedFlow<SearchViewAction> = _action

    fun setOrigin(origin: UiStation?) {
        this.origin = origin
    }

    fun setDestination(destination: UiStation?) {
        this.destination = destination
    }

    fun onUpdateBusAndTramJourneyCount(count: Int) {
        if (count < 0) {
            return
        }

        busAndTramJourneyCount = count
        viewModelScope.launch(dispatcher) {
            _state.update { it.onUpdateBusAndTramJourneyCount(count) }
        }
    }

    fun onAppInfoClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.ShowAppInfo)
        }
    }

    fun onCalculateClicked() {
        viewModelScope.launch(dispatcher) {
            if (origin == null || destination == null) {
                val error = UiSearchError.MISSING_ORIGIN_OR_DESTINATION
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            } else if (origin == destination) {
                val error = UiSearchError.SAME_ORIGIN_AND_DESTINATION
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            } else {
                val action = SearchViewAction.NavigateToFares(
                    origin = origin!!,
                    destination = destination!!,
                    busAndTramJourneyCount = busAndTramJourneyCount
                )
                _action.emit(action)
            }
        }
    }
}
