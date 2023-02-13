package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "home_should_show_empty_state_tutorial"

internal class DisableEmptyStateTutorialUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : DisableEmptyStateTutorialUseCase {

    override fun invoke() {
        preferencesManager.putBoolean(KEY, value = false)
    }
}
