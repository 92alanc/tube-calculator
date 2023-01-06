package com.alancamargo.tubecalculator.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alancamargo.tubecalculator.core.di.IoDispatcher
import com.alancamargo.tubecalculator.settings.domain.usecase.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.SetCrashLoggingEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val isCrashLoggingEnabledUseCase: IsCrashLoggingEnabledUseCase,
    private val setCrashLoggingEnabledUseCase: SetCrashLoggingEnabledUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsViewState())
    private val _action = MutableSharedFlow<SettingsViewAction>()

    val state: StateFlow<SettingsViewState> = _state
    val action: SharedFlow<SettingsViewAction> = _action

    fun onCreate() {
        val isCrashLoggingEnabled = isCrashLoggingEnabledUseCase()
        _state.update { it.setCrashLoggingEnabled(isCrashLoggingEnabled) }
    }

    fun onCrashLoggingToggled(isEnabled: Boolean) {
        setCrashLoggingEnabledUseCase(isEnabled)
        _state.update { it.setCrashLoggingEnabled(isEnabled) }
    }

    fun onBackClicked() {
        viewModelScope.launch(dispatcher) {
            _action.emit(SettingsViewAction.Finish)
        }
    }
}
