package com.alancamargo.tubecalculator.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.di.UiDelay
import com.alancamargo.tubecalculator.home.data.analytics.HomeAnalytics
import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val isFirstAccessUseCase: IsFirstAccessUseCase,
    private val disableFirstAccessUseCase: DisableFirstAccessUseCase,
    private val analytics: HomeAnalytics,
    @AppVersionName private val appVersionName: String,
    @UiDelay private val uiDelay: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    private val _action = MutableSharedFlow<HomeViewAction>()

    private var journeys = emptyList<Journey>()

    private var isAddButtonExpanded = false

    val state: StateFlow<HomeViewState> = _state
    val action: SharedFlow<HomeViewAction> = _action

    fun onCreate(isFirstLaunch: Boolean) {
        if (!isFirstLaunch) {
            return
        }

        analytics.trackScreenViewed()

        viewModelScope.launch(dispatcher) {
            if (isFirstAccessUseCase()) {
                delay(uiDelay)
                _action.emit(HomeViewAction.ShowFirstAccessDialogue)
            }
        }
    }

    fun onFirstAccessGoToSettingsClicked() {
        viewModelScope.launch(dispatcher) {
            disableFirstAccessUseCase()
            _action.emit(HomeViewAction.NavigateToSettings)
        }
    }

    fun onFirstAccessNotNowClicked() {
        viewModelScope.launch(dispatcher) {
            disableFirstAccessUseCase()
        }
    }

    fun onSettingsClicked() {
        analytics.trackSettingsClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.NavigateToSettings)
        }
    }

    fun onPrivacyPolicyClicked() {
        analytics.trackPrivacyPolicyClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.ShowPrivacyPolicyDialogue)
        }
    }

    fun onAppInfoClicked() {
        analytics.trackAppInfoClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.ShowAppInfo(appVersionName))
        }
    }

    fun onCalculateClicked() {
        analytics.trackCalculateClicked(journeys)

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.NavigateToFares(journeys))
        }
    }

    fun onJourneyReceived(journey: Journey) {
        if (journey is Journey.Rail) {
            journeys.find { it is Journey.Rail }?.let { existingRailJourney ->
                journeys = journeys - existingRailJourney
            }
        } else {
            journeys.find { it is Journey.BusAndTram }?.let { existingBusAndTramJourney ->
                journeys = journeys - existingBusAndTramJourney
            }
        }

        journeys = journeys + journey
        _state.update { it.onJourneysUpdated(journeys) }
    }

    fun onJourneyRemoved(journey: Journey) {
        analytics.trackJourneyRemoved()

        journeys = journeys - journey
        _state.update { it.onJourneysUpdated(journeys) }
    }

    fun onAddClicked() {
        if (isAddButtonExpanded) {
            collapseAddButton()
        } else {
            expandAddButton()
        }
    }

    fun onJourneyClicked(journey: Journey) {
        analytics.trackJourneyClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.EditJourney(journey))
        }
    }

    fun onAddRailJourneyClicked() {
        collapseAddButton()

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.AddRailJourney)
        }
    }

    fun onAddBusAndTramJourneyClicked() {
        collapseAddButton()

        viewModelScope.launch(dispatcher) {
            _action.emit(HomeViewAction.AddBusAndTramJourney)
        }
    }

    private fun expandAddButton() {
        _state.update { it.expandAddButton() }
        isAddButtonExpanded = true
    }

    private fun collapseAddButton() {
        _state.update { it.collapseAddButton() }
        isAddButtonExpanded = false
    }
}
