package com.alancamargo.tubecalculator.settings.ui.robots

import com.alancamargo.tubecalculator.core.test.ui.assertViewIsChecked
import com.alancamargo.tubecalculator.core.test.ui.assertViewIsNotChecked
import com.alancamargo.tubecalculator.settings.R
import com.alancamargo.tubecalculator.settings.ui.SettingsActivityTest
import io.mockk.verify

internal class SettingsActivityAssertionRobot(private val testSuite: SettingsActivityTest) {

    fun loadBannerAds() {
        verify { testSuite.mockAdLoader.loadBannerAds(target = any()) }
    }

    fun analyticsIsEnabled() {
        assertViewIsChecked(R.id.switchAnalytics)
    }

    fun crashLoggingIsEnabled() {
        assertViewIsChecked(R.id.switchCrashLogging)
    }

    fun personalisedAdsAreDisabled() {
        assertViewIsNotChecked(R.id.switchAdPersonalisation)
    }
}
