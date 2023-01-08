package com.alancamargo.tubecalculator.settings.domain.usecase

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

internal class IsAdPersonalisationEnabledUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : IsAdPersonalisationEnabledUseCase {

    override fun invoke(): Boolean = repository.isAdPersonalisationEnabled()
}
