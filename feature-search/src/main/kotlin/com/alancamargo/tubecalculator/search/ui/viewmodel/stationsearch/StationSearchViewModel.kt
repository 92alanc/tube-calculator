package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.usecase.SearchStationUseCase
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
internal class StationSearchViewModel @Inject constructor(
    private val searchStationUseCase: SearchStationUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(StationSearchViewState())
    private val _action = MutableSharedFlow<StationSearchViewAction>()

    val state: StateFlow<StationSearchViewState> = _state
    val action: SharedFlow<StationSearchViewAction> = _action

    var selectedStation: UiStation? = null
        private set

    fun searchStation(query: String) {
        viewModelScope.launch(dispatcher) {
            searchStationUseCase(query).onStart {
                _state.update { it.onLoading() }
            }.catch { throwable ->
                handleThrowable(throwable)
            }.onCompletion {
                _state.update { it.onStopLoading() }
            }.collect(::handleResult)
        }
    }

    fun onStationSelected(station: UiStation) {
        this.selectedStation = station
        _state.update { it.onSelectedStation(station) }
    }

    fun onQueryChanged(query: String?) {
        if (query.isNullOrBlank()) {
            _state.update { it.disableSearchButton() }
            _state.update { it.clearSearchResults() }
        } else {
            _state.update { it.enableSearchButton() }
        }
    }

    private suspend fun handleThrowable(throwable: Throwable) {
        val error = if (throwable is IOException) {
            UiSearchError.NETWORK
        } else {
            UiSearchError.GENERIC
        }

        _action.emit(StationSearchViewAction.ShowErrorDialogue(error))
    }

    private suspend fun handleResult(result: StationListResult) {
        when (result) {
            is StationListResult.Success -> {
                val stations = result.stations.map { it.toUi() }
                _state.update { it.onReceivedSearchResults(stations) }
            }

            is StationListResult.Empty -> {
                _state.update { it.onEmptyState() }
            }

            is StationListResult.NetworkError -> {
                val error = UiSearchError.NETWORK
                _action.emit(StationSearchViewAction.ShowErrorDialogue(error))
            }

            is StationListResult.ServerError -> {
                val error = UiSearchError.SERVER
                _action.emit(StationSearchViewAction.ShowErrorDialogue(error))
            }

            is StationListResult.GenericError -> {
                val error = UiSearchError.GENERIC
                _action.emit(StationSearchViewAction.ShowErrorDialogue(error))
            }
        }
    }
}
