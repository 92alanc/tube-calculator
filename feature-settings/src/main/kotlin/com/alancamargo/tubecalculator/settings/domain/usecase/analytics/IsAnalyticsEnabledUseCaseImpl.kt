package com.alancamargo.tubecalculator.settings.domain.usecase.analytics

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

internal class IsAnalyticsEnabledUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : IsAnalyticsEnabledUseCase {

    override fun invoke(): Boolean = repository.isAnalyticsEnabled()
}
