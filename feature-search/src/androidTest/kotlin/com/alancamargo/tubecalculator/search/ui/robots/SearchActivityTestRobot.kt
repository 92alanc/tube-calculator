package com.alancamargo.tubecalculator.search.ui.robots

import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.search.data.model.DbStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest
import io.mockk.coEvery
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

internal fun SearchActivityTest.given(
    block: SearchActivityTestRobot.() -> Unit
): SearchActivityTestRobot {
    return SearchActivityTestRobot(testSuite = this).apply(block)
}

internal class SearchActivityTestRobot(private val testSuite: SearchActivityTest) {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun launchWithBlankRailJourney() {
        val intent = SearchActivity.getIntent(context, JourneyType.RAIL)
        ActivityScenario.launch<SearchActivity>(intent)
        runBlocking { delay(200) }
    }

    fun launchWithBlankBusAndTramJourney() {
        val intent = SearchActivity.getIntent(context, JourneyType.BUS_AND_TRAM)
        ActivityScenario.launch<SearchActivity>(intent)
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
}
