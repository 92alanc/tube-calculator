package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "is_first_access"

internal class DisableFirstAccessUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : DisableFirstAccessUseCase {

    override fun invoke() {
        preferencesManager.putBoolean(KEY, value = false)
    }
}