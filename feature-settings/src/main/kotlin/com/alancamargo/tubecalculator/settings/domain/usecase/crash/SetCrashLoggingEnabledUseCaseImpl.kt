package com.alancamargo.tubecalculator.settings.domain.usecase.crash

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

internal class SetCrashLoggingEnabledUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : SetCrashLoggingEnabledUseCase {

    override fun invoke(isEnabled: Boolean) {
        repository.setCrashLoggingEnabled(isEnabled)
    }
}
