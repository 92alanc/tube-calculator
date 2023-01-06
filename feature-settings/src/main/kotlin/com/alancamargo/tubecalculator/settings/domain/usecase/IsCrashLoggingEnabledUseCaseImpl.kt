package com.alancamargo.tubecalculator.settings.domain.usecase

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

internal class IsCrashLoggingEnabledUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : IsCrashLoggingEnabledUseCase {

    override fun invoke(): Boolean = repository.isCrashLoggingEnabled()
}
