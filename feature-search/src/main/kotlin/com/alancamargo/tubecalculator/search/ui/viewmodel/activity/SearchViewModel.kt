package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
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

/**
 * Without this delay the UI won't have time to process the action
 */
private const val FIRST_ACCESS_DELAY_MILLIS = 200L

@HiltViewModel
internal class SearchViewModel(
    private val isFirstAccessUseCase: IsFirstAccessUseCase,
    private val disableFirstAccessUseCase: DisableFirstAccessUseCase,
    private val analytics: SearchAnalytics,
    private val firstAccessDelay: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    @Inject constructor(
        isFirstAccessUseCase: IsFirstAccessUseCase,
        disableFirstAccessUseCase: DisableFirstAccessUseCase,
        analytics: SearchAnalytics,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) : this(
        isFirstAccessUseCase,
        disableFirstAccessUseCase,
        analytics,
        FIRST_ACCESS_DELAY_MILLIS,
        dispatcher
    )

    private val _action = MutableSharedFlow<SearchViewAction>()

    val action: SharedFlow<SearchViewAction> = _action

    fun onStart() {
        analytics.trackScreenViewed()

        viewModelScope.launch(dispatcher) {
            if (isFirstAccessUseCase()) {
                delay(firstAccessDelay)
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
        analytics.trackSettingsClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.NavigateToSettings)
        }
    }

    fun onAppInfoClicked() {
        analytics.trackAppInfoClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.ShowAppInfo)
        }
    }

    fun onCalculateClicked(
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int
    ) {
        analytics.trackCalculateClicked(
            origin = origin?.name,
            destination = destination?.name,
            busAndTramJourneyCount = busAndTramJourneyCount
        )

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
