package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.search.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val isFirstAccessUseCase: IsFirstAccessUseCase,
    private val disableFirstAccessUseCase: DisableFirstAccessUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _action = MutableSharedFlow<SearchViewAction>()

    val action: SharedFlow<SearchViewAction> = _action

    fun onStart() {
        viewModelScope.launch(dispatcher) {
            delay(200) // Without this delay the UI won't have time to process the action

            if (isFirstAccessUseCase()) {
                _action.emit(SearchViewAction.ShowFirstAccessDialogue)
            }
        }
    }

    fun onFirstAccessDialogueDismissed() {
        viewModelScope.launch(dispatcher) {
            disableFirstAccessUseCase()
            _action.emit(SearchViewAction.NavigateToSettings)
        }
    }

    fun onSettingsClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.NavigateToSettings)
        }
    }

    fun onAppInfoClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.ShowAppInfo)
        }
    }

    fun onCalculateClicked(
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int
    ) {
        viewModelScope.launch(dispatcher) {
            if ((origin == null || destination == null) && busAndTramJourneyCount == 0) {
                val error = UiSearchError.MISSING_ORIGIN_OR_DESTINATION
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            } else if (origin != null && destination != null && origin == destination) {
                val error = UiSearchError.SAME_ORIGIN_AND_DESTINATION
                _action.emit(SearchViewAction.ShowErrorDialogue(error))
            } else {
                val action = SearchViewAction.NavigateToFares(
                    origin = origin,
                    destination = destination,
                    busAndTramJourneyCount = busAndTramJourneyCount
                )
                _action.emit(action)
            }
        }
    }
}
