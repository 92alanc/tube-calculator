package com.alancamargo.tubecalculator.fares.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.text.BulletListFormatter
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.data.work.FaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.GetFaresUseCase
import com.alancamargo.tubecalculator.fares.ui.mapping.toDomain
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
internal class FaresViewModel @Inject constructor(
    private val getFaresUseCase: GetFaresUseCase,
    private val calculateBusAndTramFareUseCase: CalculateBusAndTramFareUseCase,
    private val bulletListFormatter: BulletListFormatter,
    private val faresCacheWorkScheduler: FaresCacheWorkScheduler,
    private val analytics: FaresAnalytics,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(FaresViewState())
    private val _action = MutableSharedFlow<FaresViewAction>()

    val state: StateFlow<FaresViewState> = _state
    val action: SharedFlow<FaresViewAction> = _action

    fun onCreate(
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int
    ) {
        analytics.trackScreenViewed()

        viewModelScope.launch(dispatcher) {
            calculateBusAndTramFare(busAndTramJourneyCount)
            if (origin != null && destination != null) {
                getRailFares(origin, destination)
            } else {
                _state.update { it.onShowOnlyBusAndTramFare() }
            }

            faresCacheWorkScheduler.scheduleFaresCacheBackgroundWork()
        }
    }

    fun onDismissErrorDialogue() {
        viewModelScope.launch(dispatcher) {
            _action.emit(FaresViewAction.Finish)
        }
    }

    fun onNewSearchClicked() {
        analytics.trackNewSearchClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(FaresViewAction.NavigateToSearch)
        }
    }

    fun onMessagesButtonClicked(messages: List<String>) {
        analytics.trackMessagesClicked()

        viewModelScope.launch(dispatcher) {
            val text = bulletListFormatter.getBulletList(messages)
            _action.emit(FaresViewAction.ShowMessagesDialogue(text))
        }
    }

    private suspend fun getRailFares(origin: UiStation, destination: UiStation) {
        getFaresUseCase.invoke(
            origin = origin.toDomain(),
            destination = destination.toDomain()
        ).onStart {
            _state.update { it.onLoading() }
        }.catch { throwable ->
            logger.error(throwable)
            handleThrowable(throwable)
        }.onCompletion {
            _state.update { it.onStopLoading() }
        }.collect { result ->
            if (result is FareListResult.ServerError || result is FareListResult.GenericError) {
                val message = "Origin: ${origin.name}. Destination: ${destination.name}. Result: $result"
                logger.debug(message)
            }

            handleResult(result)
        }
    }

    private fun calculateBusAndTramFare(busAndTramJourneyCount: Int) {
        calculateBusAndTramFareUseCase(busAndTramJourneyCount)?.let { busAndTramFare ->
            _state.update { it.onReceivedBusAndTramFare(busAndTramFare) }
        }
    }

    private suspend fun handleThrowable(throwable: Throwable) {
        val error = if (throwable is IOException) {
            UiFaresError.NETWORK
        } else {
            UiFaresError.GENERIC
        }

        _action.emit(FaresViewAction.ShowErrorDialogue(error))
    }

    private suspend fun handleResult(result: FareListResult) {
        when (result) {
            is FareListResult.Success -> _state.update { it.onReceivedRailFares(result.fareList) }

            is FareListResult.InvalidQueryError -> {
                val error = UiFaresError.INVALID_QUERY
                _action.emit(FaresViewAction.ShowErrorDialogue(error))
            }

            is FareListResult.NetworkError -> {
                val error = UiFaresError.NETWORK
                _action.emit(FaresViewAction.ShowErrorDialogue(error))
            }

            is FareListResult.ServerError -> {
                val error = UiFaresError.SERVER
                _action.emit(FaresViewAction.ShowErrorDialogue(error))
            }

            is FareListResult.GenericError -> {
                val error = UiFaresError.GENERIC
                _action.emit(FaresViewAction.ShowErrorDialogue(error))
            }
        }
    }
}
