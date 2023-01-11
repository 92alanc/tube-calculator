package com.alancamargo.tubecalculator.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.IsAdPersonalisationEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.SetAdPersonalisationEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.analytics.IsAnalyticsEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.analytics.SetAnalyticsEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.SetCrashLoggingEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val isCrashLoggingEnabledUseCase: IsCrashLoggingEnabledUseCase,
    private val setCrashLoggingEnabledUseCase: SetCrashLoggingEnabledUseCase,
    private val isAdPersonalisationEnabledUseCase: IsAdPersonalisationEnabledUseCase,
    private val setAdPersonalisationEnabledUseCase: SetAdPersonalisationEnabledUseCase,
    private val isAnalyticsEnabledUseCase: IsAnalyticsEnabledUseCase,
    private val setAnalyticsEnabledUseCase: SetAnalyticsEnabledUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    private val _action = MutableSharedFlow<SettingsViewAction>()

    val state: StateFlow<SettingsViewState> = _state
    val action: SharedFlow<SettingsViewAction> = _action

    fun onCreate() {
        val isCrashLoggingEnabled = isCrashLoggingEnabledUseCase()
        val isAdPersonalisationEnabled = isAdPersonalisationEnabledUseCase()
        val isAnalyticsEnabled = isAnalyticsEnabledUseCase()

        _state.update {
            it.setAllValues(
                isCrashLoggingEnabled = isCrashLoggingEnabled,
                isAdPersonalisationEnabled = isAdPersonalisationEnabled,
                isAnalyticsEnabled = isAnalyticsEnabled
            )
        }
    }

    fun onCrashLoggingToggled(isEnabled: Boolean) {
        setCrashLoggingEnabledUseCase(isEnabled)
        _state.update { it.setCrashLoggingEnabled(isEnabled) }
    }

    fun onAdPersonalisationToggled(isEnabled: Boolean) {
        setAdPersonalisationEnabledUseCase(isEnabled)
        _state.update { it.setAdPersonalisationEnabled(isEnabled) }
    }

    fun onAnalyticsToggled(isEnabled: Boolean) {
        setAnalyticsEnabledUseCase(isEnabled)
        _state.update { it.setAnalyticsEnabled(isEnabled) }
    }

    fun onBackClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SettingsViewAction.Finish)
        }
    }
}
