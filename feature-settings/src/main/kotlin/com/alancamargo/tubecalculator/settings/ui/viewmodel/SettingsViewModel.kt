package com.alancamargo.tubecalculator.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.alancamargo.tubecalculator.settings.domain.usecase.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.SetCrashLoggingEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val isCrashLoggingEnabledUseCase: IsCrashLoggingEnabledUseCase,
    private val setCrashLoggingEnabledUseCase: SetCrashLoggingEnabledUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())

    val state: StateFlow<SettingsViewState> = _state

    fun onCreate() {
        val isCrashLoggingEnabled = isCrashLoggingEnabledUseCase()
        _state.update { it.setCrashLoggingEnabled(isCrashLoggingEnabled) }
    }

    fun onCrashLoggingToggled(isEnabled: Boolean) {
        setCrashLoggingEnabledUseCase(isEnabled)
        _state.update { it.setCrashLoggingEnabled(isEnabled) }
    }
}
