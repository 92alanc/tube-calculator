package com.alancamargo.tubecalculator.search.ui.robots

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.search.data.model.DbStation
import com.alancamargo.tubecalculator.search.data.model.ModeResponse
import com.alancamargo.tubecalculator.search.ui.SearchActivity
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest
import com.alancamargo.tubecalculator.search.ui.testtools.BUS_AND_TRAM_JOURNEY_COUNT
import com.alancamargo.tubecalculator.search.ui.testtools.STATION_1
import com.alancamargo.tubecalculator.search.ui.testtools.STATION_2
import com.alancamargo.tubecalculator.search.ui.testtools.stubUiStation
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
        launch(intent)
    }

    fun launchWithBlankBusAndTramJourney() {
        val intent = SearchActivity.getIntent(context, JourneyType.BUS_AND_TRAM)
        launch(intent)
    }

    fun launchWithPreFilledRailJourney() {
        val journey = Journey.Rail(
            origin = stubUiStation(STATION_1),
            destination = stubUiStation(STATION_2)
        )
        val intent = SearchActivity.getIntent(context, journey)
        launch(intent)
    }

    fun launchWithPreFilledBusAndTramJourney() {
        val journey = Journey.BusAndTram(BUS_AND_TRAM_JOURNEY_COUNT)
        val intent = SearchActivity.getIntent(context, journey)
        launch(intent)
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

    private fun launch(intent: Intent) {
        ActivityScenario.launch<SearchActivity>(intent).onActivity {
            testSuite.activity = it
        }
        runBlocking { delay(200) }
    }
}
