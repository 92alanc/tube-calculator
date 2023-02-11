package com.alancamargo.tubecalculator.search.ui.robots

import androidx.test.core.app.ActivityScenario
import com.alancamargo.tubecalculator.search.data.model.DbStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

private const val KEY_FIRST_ACCESS = "is_first_access"

internal fun SearchActivityTest.given(
    block: SearchActivityTestRobot.() -> Unit
): SearchActivityTestRobot {
    return SearchActivityTestRobot(testSuite = this).apply(block)
}

internal class SearchActivityTestRobot(private val testSuite: SearchActivityTest) {

    fun launchOnFirstAccess() {
        setIsFirstAccess(isFirstAccess = true)
        launch()
    }

    fun launchAfterFirstAccess() {
        setIsFirstAccess(isFirstAccess = false)
        launch()
    }

    fun withSearchResult(query: String, stationName: String) {
        val modes = listOf(ModeResponse.UNDERGROUND, ModeResponse.OVERGROUND)
        val modesJson = Json.encodeToString(modes)

        coEvery {
            testSuite.mockSearchDao.searchStation(query)
        } returns listOf(
            DbStation(
                id = "12345",
                name = stationName,
                modesJson = modesJson
            )
        )
    }

    fun withGenericError() {
        coEvery {
            testSuite.mockSearchDao.searchStation(query = any())
        } throws IOException()
    }

    infix fun withAction(action: SearchActivityActionRobot.() -> Unit): SearchActivityActionRobot {
        return SearchActivityActionRobot(testSuite).apply(action)
    }

    infix fun then(
        assertion: SearchActivityAssertionRobot.() -> Unit
    ): SearchActivityAssertionRobot {
        return SearchActivityAssertionRobot(testSuite).apply(assertion)
    }

    private fun launch() {
        ActivityScenario.launch(SearchActivity::class.java)
        runBlocking { delay(200) }
    }

    private fun setIsFirstAccess(isFirstAccess: Boolean) {
        every {
            testSuite.mockPreferencesManager.getBoolean(KEY_FIRST_ACCESS, defaultValue = true)
        } returns isFirstAccess
    }
}
