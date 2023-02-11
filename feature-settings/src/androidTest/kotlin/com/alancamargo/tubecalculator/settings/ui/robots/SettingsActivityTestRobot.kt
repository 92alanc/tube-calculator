package com.alancamargo.tubecalculator.settings.ui.robots

import androidx.test.core.app.ActivityScenario
import com.alancamargo.tubecalculator.settings.ui.SettingsActivity
import com.alancamargo.tubecalculator.settings.ui.SettingsActivityTest

internal fun SettingsActivityTest.given(
    block: SettingsActivityTestRobot.() -> Unit
): SettingsActivityTestRobot {
    return SettingsActivityTestRobot(testSuite = this).apply(block)
}

internal class SettingsActivityTestRobot(private val testSuite: SettingsActivityTest) {

    fun launch() {
        ActivityScenario.launch(SettingsActivity::class.java)
    }

    infix fun then(
        assertion: SettingsActivityAssertionRobot.() -> Unit
    ): SettingsActivityAssertionRobot {
        return SettingsActivityAssertionRobot(testSuite).apply(assertion)
    }
}
