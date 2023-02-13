package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "home_should_show_delete_journey_tutorial"

internal class ShouldShowDeleteJourneyTutorialUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ShouldShowDeleteJourneyTutorialUseCase {

    override fun invoke(): Boolean {
        return preferencesManager.getBoolean(KEY, defaultValue = true)
    }
}
