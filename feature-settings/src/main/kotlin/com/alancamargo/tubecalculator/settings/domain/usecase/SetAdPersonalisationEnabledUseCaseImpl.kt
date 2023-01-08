package com.alancamargo.tubecalculator.settings.domain.usecase

import com.alancamargo.tubecalculator.settings.domain.repository.SettingsRepository
import javax.inject.Inject

internal class SetAdPersonalisationEnabledUseCaseImpl @Inject constructor(
    private val repository: SettingsRepository
) : SetAdPersonalisationEnabledUseCase {

    override fun invoke(isEnabled: Boolean) {
        repository.setAdPersonalisationEnabled(isEnabled)
    }
}
