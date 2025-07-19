package com.alancamargo.tubecalculator.fares.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.text.BulletListFormatter
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.data.work.RailFaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateCheapestTotalFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.GetRailFaresUseCase
import com.alancamargo.tubecalculator.fares.ui.mapping.toDomain
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
internal class FaresViewModel @Inject constructor(
    private val getRailFaresUseCase: GetRailFaresUseCase,
    private val calculateBusAndTramFareUseCase: CalculateBusAndTramFareUseCase,
    private val calculateCheapestTotalFareUseCase: CalculateCheapestTotalFareUseCase,
    private val bulletListFormatter: BulletListFormatter,
    private val railFaresCacheWorkScheduler: RailFaresCacheWorkScheduler,
    private val analytics: FaresAnalytics,
    private val logger: Logger,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(FaresViewState())
    private val _action = MutableSharedFlow<FaresViewAction>()

    val state: StateFlow<FaresViewState> = _state
    val action: SharedFlow<FaresViewAction> = _action

    private val fares = mutableListOf<Fare>()

    fun onCreate(
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int,
        isFirstLaunch: Boolean
    ) {
        if (!isFirstLaunch) {
            return
        }

        analytics.trackScreenViewed()

        if (origin != null && destination != null) {
            viewModelScope.launch(dispatcher) {
                getRailFares(origin, destination, busAndTramJourneyCount)
            }
        } else {
            calculateBusAndTramFare(busAndTramJourneyCount)
            calculateCheapestTotalFare()
        }
    }

    fun onDismissErrorDialogue() {
        sendAction(FaresViewAction.NavigateToHome)
    }

    fun onNewSearchClicked() {
        analytics.trackNewSearchClicked()
        sendAction(FaresViewAction.NavigateToHome)
    }

    fun onMessagesButtonClicked(messages: List<String>) {
        analytics.trackMessagesClicked()

        val text = bulletListFormatter.getBulletList(messages)
        sendAction(FaresViewAction.ShowMessagesDialogue(text))
    }

    private suspend fun getRailFares(
        origin: UiStation,
        destination: UiStation,
        busAndTramJourneyCount: Int
    ) {
        getRailFaresUseCase.invoke(
            origin = origin.toDomain(),
            destination = destination.toDomain()
        ).onStart {
            _state.update { it.onLoading() }
        }.catch { throwable ->
            logger.error(throwable)
            handleRailFaresError(throwable)
        }.onCompletion {
            _state.update { it.onStopLoading() }
        }.collect { result ->
            if (result is RailFaresResult.GenericError) {
                val message = "Origin: ${origin.name}. Destination: ${destination.name}. Result: $result"
                logger.debug(message)
            }

            handleRailFaresResult(result, busAndTramJourneyCount)
        }
    }

    private fun calculateBusAndTramFare(busAndTramJourneyCount: Int) {
        calculateBusAndTramFareUseCase(busAndTramJourneyCount)?.let { busAndTramFare ->
            fares.add(busAndTramFare)
            _state.update { it.onReceivedBusAndTramFare(busAndTramFare) }
        }
    }

    private fun calculateCheapestTotalFare() {
        val cheapestTotalFare = calculateCheapestTotalFareUseCase(fares)
        _state.update { it.onReceivedCheapestTotalFare(cheapestTotalFare) }
    }

    private fun handleRailFaresError(throwable: Throwable) {
        val error = if (throwable is IOException) {
            UiFaresError.NETWORK
        } else {
            UiFaresError.GENERIC
        }

        sendAction(FaresViewAction.ShowErrorDialogue(error))
    }

    private fun handleRailFaresResult(result: RailFaresResult, busAndTramJourneyCount: Int) {
        when (result) {
            is RailFaresResult.Success -> {
                fares.addAll(result.railFares)
                _state.update { it.onReceivedRailFares(result.railFares) }

                calculateBusAndTramFare(busAndTramJourneyCount)
                calculateCheapestTotalFare()
                railFaresCacheWorkScheduler.scheduleRailFaresCacheBackgroundWork()
            }

            is RailFaresResult.InvalidQueryError -> {
                val error = UiFaresError.INVALID_QUERY
                sendAction(FaresViewAction.ShowErrorDialogue(error))
            }

            is RailFaresResult.NetworkError -> {
                val error = UiFaresError.NETWORK
                sendAction(FaresViewAction.ShowErrorDialogue(error))
            }

            is RailFaresResult.GenericError -> {
                val error = UiFaresError.GENERIC
                sendAction(FaresViewAction.ShowErrorDialogue(error))
            }
        }
    }

    private fun sendAction(action: FaresViewAction) = viewModelScope.launch(dispatcher) {
        _action.emit(action)
    }
}
