package com.alancamargo.tubecalculator.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.core.di.UiDelay
import com.alancamargo.tubecalculator.home.data.analytics.HomeAnalytics
import com.alancamargo.tubecalculator.home.domain.usecase.DisableDeleteJourneyTutorialUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.ShouldShowDeleteJourneyTutorialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val isFirstAccessUseCase: IsFirstAccessUseCase,
    private val disableFirstAccessUseCase: DisableFirstAccessUseCase,
    private val shouldShowDeleteJourneyTutorialUseCase: ShouldShowDeleteJourneyTutorialUseCase,
    private val disableDeleteJourneyTutorialUseCase: DisableDeleteJourneyTutorialUseCase,
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
                _state.update { it.showAddJourneyTutorial() }
            }
        }
    }

    fun onFirstAccessGoToSettingsClicked() {
        disableFirstAccessUseCase()
        sendAction(HomeViewAction.NavigateToSettings)
    }

    fun onFirstAccessNotNowClicked() {
        disableFirstAccessUseCase()
    }

    fun onSettingsClicked() {
        analytics.trackSettingsClicked()
        sendAction(HomeViewAction.NavigateToSettings)
    }

    fun onPrivacyPolicyClicked() {
        analytics.trackPrivacyPolicyClicked()
        sendAction(HomeViewAction.ShowPrivacyPolicyDialogue)
    }

    fun onAppInfoClicked() {
        analytics.trackAppInfoClicked()
        sendAction(HomeViewAction.ShowAppInfo(appVersionName))
    }

    fun onCalculateClicked() {
        analytics.trackCalculateClicked(journeys)
        sendAction(HomeViewAction.NavigateToFares(journeys))
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

        if (shouldShowDeleteJourneyTutorialUseCase()) {
            val illustrationAssetName = "delete_journey.gif"
            sendAction(HomeViewAction.ShowDeleteJourneyTutorial(illustrationAssetName))
            disableDeleteJourneyTutorialUseCase()
        }
    }

    fun onJourneyRemoved(journeyPosition: Int) {
        analytics.trackJourneyRemoved()

        journeys = journeys - journeys[journeyPosition]
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
        sendAction(HomeViewAction.EditJourney(journey))
    }

    fun onAddRailJourneyClicked() {
        collapseAddButton()
        sendAction(HomeViewAction.AddRailJourney)
    }

    fun onAddBusAndTramJourneyClicked() {
        collapseAddButton()
        sendAction(HomeViewAction.AddBusAndTramJourney)
    }

    private fun sendAction(action: HomeViewAction) = viewModelScope.launch(dispatcher) {
        _action.emit(action)
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
