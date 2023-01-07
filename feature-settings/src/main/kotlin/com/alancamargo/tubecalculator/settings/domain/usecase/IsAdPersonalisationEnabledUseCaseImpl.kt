package com.alancamargo.tubecalculator.settings.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "gad_has_consent_for_cookies"

internal class IsAdPersonalisationEnabledUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : IsAdPersonalisationEnabledUseCase {

    override fun invoke(): Boolean {
        val intValue = preferencesManager.getInt(KEY, defaultValue = 0)
        return intValue == 1
    }
}
