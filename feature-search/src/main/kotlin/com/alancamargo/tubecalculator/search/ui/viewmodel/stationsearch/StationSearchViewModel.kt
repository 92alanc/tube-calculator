package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.usecase.GetMinQueryLengthUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.SearchStationUseCase
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StationSearchViewModel @Inject constructor(
    private val searchStationUseCase: SearchStationUseCase,
    private val getMinQueryLengthUseCase: GetMinQueryLengthUseCase,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(StationSearchViewState())
    private val _action = MutableSharedFlow<StationSearchViewAction>()

    val state: StateFlow<StationSearchViewState> = _state
    val action: SharedFlow<StationSearchViewAction> = _action

    private var hasSelectedStation = false

    fun onCreate(searchType: SearchType, station: UiStation?) {
        _state.update { it.onReceivedSearchType(searchType) }
        onStationSelected(station)
    }

    fun onQueryChanged(query: String) {
        if (query.isBlank()) {
            onStationSelected(station = null)
            return
        }

        val minQueryLength = getMinQueryLengthUseCase()
        if (hasSelectedStation || query.length < minQueryLength) {
            return
        }

        viewModelScope.launch(dispatcher) {
            searchStationUseCase(query).catch { throwable ->
                logger.error(throwable)
                val error = UiSearchError.GENERIC
                _action.emit(StationSearchViewAction.ShowErrorDialogue(error))
            }.collect(::handleResult)
        }
    }

    fun onStationSelected(station: UiStation?) {
        hasSelectedStation = station != null
        _state.update { it.onStationSelected(station) }
    }

    private suspend fun handleResult(result: StationListResult) {
        when (result) {
            is StationListResult.Success -> {
                val stations = result.stations.map { it.toUi() }
                _state.update { it.onReceivedStations(stations) }
            }

            is StationListResult.GenericError -> {
                val error = UiSearchError.GENERIC
                _action.emit(StationSearchViewAction.ShowErrorDialogue(error))
            }
        }
    }
}
