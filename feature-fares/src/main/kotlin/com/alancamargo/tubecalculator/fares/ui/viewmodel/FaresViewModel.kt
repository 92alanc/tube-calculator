package com.alancamargo.tubecalculator.fares.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.tools.BulletListFormatter
import com.alancamargo.tubecalculator.core.di.IoDispatcher
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
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(FaresViewState())
    private val _action = MutableSharedFlow<FaresViewAction>()

    val state: StateFlow<FaresViewState> = _state
    val action: SharedFlow<FaresViewAction> = _action

    fun onCreate(
        origin: UiStation,
        destination: UiStation,
        busAndTramJourneyCount: Int
    ) {
        viewModelScope.launch(dispatcher) {
            getRailFares(origin, destination)
            calculateBusAndTramFare(busAndTramJourneyCount)
        }
    }

    fun onDismissErrorDialogue() {
        viewModelScope.launch(dispatcher) {
            _action.emit(FaresViewAction.Finish)
        }
    }

    fun onNewSearchClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(FaresViewAction.NavigateToSearch)
        }
    }

    fun onMessagesButtonClicked(messages: List<String>) {
        viewModelScope.launch(dispatcher) {
            val text = bulletListFormatter.getBulletList(messages)
            _action.emit(FaresViewAction.ShowMessagesDialogue(text))
        }
    }

    private suspend fun getRailFares(
        origin: UiStation,
        destination: UiStation
    ) {
        getFaresUseCase.invoke(
            origin = origin.toDomain(),
            destination = destination.toDomain()
        ).onStart {
            _state.update { it.onLoading() }
        }.catch { throwable ->
            handleThrowable(throwable)
        }.onCompletion {
            _state.update { it.onStopLoading() }
        }.collect(::handleResult)
    }

    private fun calculateBusAndTramFare(busAndTramJourneyCount: Int) {
        val busAndTramFare = calculateBusAndTramFareUseCase(busAndTramJourneyCount)
        _state.update { it.onReceivedBusAndTramFare(busAndTramFare) }
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
