package com.alancamargo.tubecalculator.settings.domain.usecase

internal interface SetCrashLoggingEnabledUseCase {

    operator fun invoke(isEnabled: Boolean)
}
