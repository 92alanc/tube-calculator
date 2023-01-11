package com.alancamargo.tubecalculator.settings.domain.usecase.crash

internal interface SetCrashLoggingEnabledUseCase {

    operator fun invoke(isEnabled: Boolean)
}
