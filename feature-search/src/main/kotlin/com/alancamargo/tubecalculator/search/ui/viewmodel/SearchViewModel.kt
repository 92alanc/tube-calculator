package com.alancamargo.tubecalculator.search.ui.viewmodel

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
internal class SearchViewModel @Inject constructor(
    private val searchStationUseCase: SearchStationUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(SearchViewState())
    private val _action = MutableSharedFlow<SearchViewAction>()

    private var origin: UiStation? = null
    private var destination: UiStation? = null

    val state: StateFlow<SearchViewState> = _state
    val action: SharedFlow<SearchViewAction> = _action

    fun searchOrigin(query: String) {
        viewModelScope.launch(dispatcher) {
            searchStationUseCase(query).onStart {
                _state.update { it.onLoadingOriginSearchResults() }
            }.catch { throwable ->
                handleThrowable(throwable)
            }.onCompletion {
                _state.update { it.onStopLoadingOriginSearchResults() }
            }.collect(::handleOriginResult)
        }
    }

    fun searchDestination(query: String) {
        viewModelScope.launch(dispatcher) {
            searchStationUseCase(query).onStart {
                _state.update { it.onLoadingDestinationSearchResults() }
            }.catch { throwable ->
                handleThrowable(throwable)
            }.onCompletion {
                _state.update { it.onStopLoadingDestinationSearchResults() }
            }.collect(::handleDestinationResult)
        }
    }

    fun onUpdateBusAndTramJourneyCount(count: Int) {
        if (count < 0) {
            return
        }

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
                    busAndTramJourneyCount = _state.value.busAndTramJourneyCount
                )
                _action.emit(action)
            }
        }
    }

    private suspend fun handleThrowable(throwable: Throwable) {
        val error = if (throwable is IOException) {
            UiSearchError.NETWORK
        } else {
            UiSearchError.GENERIC
        }

        _action.emit(SearchViewAction.ShowErrorDialogue(error))
    }

    private suspend fun handleOriginResult(result: StationListResult) {
        when (result) {
            is StationListResult.Success -> {
                val stations = result.stations.map { it.toUi() }
                _state.update { it.onReceivedOriginSearchResults(stations) }
            }

            is StationListResult.Empty -> {
                _state.update { it.onOriginEmptyState() }
            }

            is StationListResult.NetworkError -> {
                val error = UiSearchError.NETWORK
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }

            is StationListResult.ServerError -> {
                val error = UiSearchError.SERVER
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }

            is StationListResult.GenericError -> {
                val error = UiSearchError.GENERIC
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }
        }
    }

    private suspend fun handleDestinationResult(result: StationListResult) {
        when (result) {
            is StationListResult.Success -> {
                val stations = result.stations.map { it.toUi() }
                _state.update { it.onReceivedDestinationSearchResults(stations) }
            }

            is StationListResult.Empty -> {
                _state.update { it.onDestinationEmptyState() }
            }

            is StationListResult.NetworkError -> {
                val error = UiSearchError.NETWORK
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }

            is StationListResult.ServerError -> {
                val error = UiSearchError.SERVER
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }

            is StationListResult.GenericError -> {
                val error = UiSearchError.GENERIC
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            }
        }
    }
}
