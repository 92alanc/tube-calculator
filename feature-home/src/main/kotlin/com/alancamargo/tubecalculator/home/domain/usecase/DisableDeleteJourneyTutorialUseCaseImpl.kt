package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "home_should_show_delete_journey_tutorial"

internal class DisableDeleteJourneyTutorialUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : DisableDeleteJourneyTutorialUseCase {

    override fun invoke() {
        preferencesManager.putBoolean(KEY, value = false)
    }
}
