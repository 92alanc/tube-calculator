package com.alancamargo.tubecalculator.home.domain.usecase

import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import javax.inject.Inject

private const val KEY = "is_first_access"

internal class IsFirstAccessUseCaseImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : IsFirstAccessUseCase {

    override fun invoke(): Boolean = preferencesManager.getBoolean(KEY, defaultValue = true)
}