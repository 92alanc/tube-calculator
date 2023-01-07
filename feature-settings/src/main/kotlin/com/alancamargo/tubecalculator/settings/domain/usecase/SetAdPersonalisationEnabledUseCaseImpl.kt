package com.alancamargo.tubecalculator.settings.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "gad_has_consent_for_cookies"

internal class SetAdPersonalisationEnabledUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : SetAdPersonalisationEnabledUseCase {

    override fun invoke(isEnabled: Boolean) {
        val value = if (isEnabled) 1 else 0
        preferencesManager.putInt(KEY, value)
    }
}
