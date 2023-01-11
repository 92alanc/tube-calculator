package com.alancamargo.tubecalculator.settings.domain.usecase.analytics

internal interface SetAnalyticsEnabledUseCase {

    operator fun invoke(isEnabled: Boolean)
}
