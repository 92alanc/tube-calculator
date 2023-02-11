package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.di.UiDelay
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
    private val analytics: SearchAnalytics,
    @AppVersionName private val appVersionName: String,
    @UiDelay private val uiDelay: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _action = MutableSharedFlow<SearchViewAction>()

    val action: SharedFlow<SearchViewAction> = _action

    fun onCreate(isFirstLaunch: Boolean) {
        if (!isFirstLaunch) {
            return
        }

        viewModelScope.launch(dispatcher) {
            delay(uiDelay)
            _action.emit(SearchViewAction.AttachFragments)
        }
    }

    fun onStart() {
        analytics.trackScreenViewed()

        viewModelScope.launch(dispatcher) {
            if (isFirstAccessUseCase()) {
                delay(uiDelay)
                _action.emit(SearchViewAction.ShowFirstAccessDialogue)
            }
        }
    }

    fun onFirstAccessGoToSettingsClicked() {
        viewModelScope.launch(dispatcher) {
            disableFirstAccessUseCase()
            _action.emit(SearchViewAction.NavigateToSettings)
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
            _action.emit(SearchViewAction.NavigateToSettings)
        }
    }

    fun onPrivacyPolicyClicked() {
        analytics.trackPrivacyPolicyClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.ShowPrivacyPolicyDialogue)
        }
    }

    fun onAppInfoClicked() {
        analytics.trackAppInfoClicked()

        viewModelScope.launch(dispatcher) {
            _action.emit(SearchViewAction.ShowAppInfo(appVersionName))
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
