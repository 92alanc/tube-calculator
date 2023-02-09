package com.alancamargo.tubecalculator.search.ui.robots

import androidx.test.core.app.ActivityScenario
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest
import io.mockk.every

private const val KEY_FIRST_ACCESS = "is_first_access"

internal fun SearchActivityTest.given(
    block: SearchActivityTestRobot.() -> Unit
): SearchActivityTestRobot {
    return SearchActivityTestRobot(testSuite = this).apply(block)
}

internal class SearchActivityTestRobot(private val testSuite: SearchActivityTest) {

    fun launchOnFirstAccess() {
        setIsFirstAccess(isFirstAccess = true)
        ActivityScenario.launch(SearchActivity::class.java)
    }

    fun launchAfterFirstAccess() {
        setIsFirstAccess(isFirstAccess = false)
        ActivityScenario.launch(SearchActivity::class.java)
    }

    infix fun withAction(action: SearchActivityActionRobot.() -> Unit): SearchActivityActionRobot {
        return SearchActivityActionRobot(testSuite).apply(action)
    }

    infix fun then(
        assertion: SearchActivityAssertionRobot.() -> Unit
    ): SearchActivityAssertionRobot {
        return SearchActivityAssertionRobot(testSuite).apply(assertion)
    }

    private fun setIsFirstAccess(isFirstAccess: Boolean) {
        every {
            testSuite.mockPreferencesManager.getBoolean(KEY_FIRST_ACCESS, defaultValue = true)
        } returns isFirstAccess
    }
}
