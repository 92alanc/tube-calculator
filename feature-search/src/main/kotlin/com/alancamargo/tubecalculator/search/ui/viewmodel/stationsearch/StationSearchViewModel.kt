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
    private val getMinQueryLengthUseCase: GetMinQueryLengthUseCase,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(StationSearchViewState())
    private val _action = MutableSharedFlow<StationSearchViewAction>()

    val state: StateFlow<StationSearchViewState> = _state
    val action: SharedFlow<StationSearchViewAction> = _action

    var selectedStation: UiStation? = null
        private set

    fun onStationSelected(station: UiStation) {
        this.selectedStation = station
        _state.update { it.onStationSelected(station) }
    }

    fun onQueryChanged(query: String?) {
        val trimmedQuery = query?.trim()

        if (trimmedQuery.isNullOrBlank()) {
            _state.update { it.clearQuery() }
            selectedStation = null
        } else {
            val minQueryLength = getMinQueryLengthUseCase()
            val isTooShort = trimmedQuery.length < minQueryLength

            if (isTooShort) {
                return
            }

            viewModelScope.launch(dispatcher) {
                searchStation(trimmedQuery)
            }
        }
    }

    private suspend fun searchStation(query: String) {
        searchStationUseCase(query).onStart {
            _state.update { it.onLoading() }
        }.catch { throwable ->
            logger.error(throwable)
            handleThrowable(throwable)
        }.onCompletion {
            _state.update { it.onStopLoading() }
        }.collect { result ->
            val isServerError = result is StationListResult.ServerError
            val isGenericError = result is StationListResult.GenericError

            if (isServerError || isGenericError) {
                logger.debug("Query: $query. Result: $result")
            }

            handleResult(result)
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

                if (stations.size == 1) {
                    val station = stations.first()
                    onStationSelected(station)
                }

                _state.update { it.onReceivedSearchResults(stations) }
            }

            is StationListResult.Empty -> _state.update { it.onEmptyState() }

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
