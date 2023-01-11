package com.alancamargo.tubecalculator.settings.domain.usecase.analytics

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

internal class SetAnalyticsEnabledUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : SetAnalyticsEnabledUseCase {

    override fun invoke(isEnabled: Boolean) {
        repository.setAnalyticsEnabled(isEnabled)
    }
}
