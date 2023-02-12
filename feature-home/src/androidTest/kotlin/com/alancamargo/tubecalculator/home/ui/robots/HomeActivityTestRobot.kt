package com.alancamargo.tubecalculator.home.ui.robots

import androidx.test.core.app.ActivityScenario
import com.alancamargo.tubecalculator.home.ui.HomeActivity
import com.alancamargo.tubecalculator.home.ui.HomeActivityTest
import io.mockk.every

private const val KEY_FIRST_ACCESS = "is_first_access"

internal fun HomeActivityTest.given(
    block: HomeActivityTestRobot.() -> Unit
): HomeActivityTestRobot {
    return HomeActivityTestRobot(testSuite = this).apply(block)
}

internal class HomeActivityTestRobot(private val testSuite: HomeActivityTest) {

    fun launchOnFirstAccess() {
        setIsFirstAccess(isFirstAccess = true)
        launch()
    }

    fun launchAfterFirstAccess() {
        setIsFirstAccess(isFirstAccess = false)
        launch()
    }

    infix fun withAction(action: HomeActivityActionRobot.() -> Unit): HomeActivityActionRobot {
        return HomeActivityActionRobot(testSuite).apply(action)
    }

    infix fun then(
        assertion: HomeActivityAssertionRobot.() -> Unit
    ): HomeActivityAssertionRobot {
        return HomeActivityAssertionRobot(testSuite).apply(assertion)
    }

    private fun launch() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    private fun setIsFirstAccess(isFirstAccess: Boolean) {
        every {
            testSuite.mockPreferencesManager.getBoolean(KEY_FIRST_ACCESS, defaultValue = true)
        } returns isFirstAccess
    }
}
