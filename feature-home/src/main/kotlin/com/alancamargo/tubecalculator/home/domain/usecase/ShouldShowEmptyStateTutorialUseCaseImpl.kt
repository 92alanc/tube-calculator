package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "home_should_show_empty_state_tutorial"

internal class ShouldShowEmptyStateTutorialUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ShouldShowEmptyStateTutorialUseCase {

    override fun invoke(): Boolean {
        return preferencesManager.getBoolean(KEY, defaultValue = true)
    }
}
