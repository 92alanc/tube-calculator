package com.alancamargo.tubecalculator.fares.ui.robots

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.core.test.web.delayWebResponse
import com.alancamargo.tubecalculator.core.test.web.disconnect
import com.alancamargo.tubecalculator.core.test.web.mockWebError
import com.alancamargo.tubecalculator.core.test.web.mockWebResponse
import com.alancamargo.tubecalculator.fares.testtools.stubUiStation
import com.alancamargo.tubecalculator.fares.ui.FaresActivity
import com.alancamargo.tubecalculator.fares.ui.FaresActivityTest
import java.net.HttpURLConnection

internal fun FaresActivityTest.given(
    block: FaresActivityTestRobot.() -> Unit
): FaresActivityTestRobot {
    return FaresActivityTestRobot(testSuite = this).apply(block)
}

internal class FaresActivityTestRobot(private val testSuite: FaresActivityTest) {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun launchWithBusAndTramFaresOnly() {
        val intent = getIntentWithBusAndTramJourneyCount()
        launch(intent)
    }

    fun launchWithSuccess() {
        val intent = getIntentWithOriginAndDestination()

        launchWithPrecondition(
            beforeLaunch = { mockWebResponse(jsonAssetPath = "fares_success.json") },
            intent = intent
        )
    }

    fun launchWithDelayedWebResponse() {
        val intent = getIntentWithOriginAndDestination()

        launchWithPrecondition(
            beforeLaunch = { delayWebResponse() },
            intent = intent
        )
    }

    fun launchDisconnected() {
        val intent = getIntentWithOriginAndDestination()

        launchWithPrecondition(
            beforeLaunch = { disconnect() },
            intent = intent
        )
    }

    fun launchWithInvalidQueryError() {
        val intent = getIntentWithOriginAndDestination()

        launchWithPrecondition(
            beforeLaunch = { mockWebResponse(jsonAssetPath = "fares_invalid_query.json") },
            intent = intent
        )
    }

    fun launchWithUnknownError() {
        val intent = getIntentWithOriginAndDestination()

        launchWithPrecondition(
            beforeLaunch = { mockWebError(HttpURLConnection.HTTP_NOT_FOUND) },
            intent = intent
        )
    }

    infix fun withAction(action: FaresActivityActionRobot.() -> Unit): FaresActivityActionRobot {
        return FaresActivityActionRobot(testSuite).apply(action)
    }

    infix fun then(
        assertion: FaresActivityAssertionRobot.() -> Unit
    ): FaresActivityAssertionRobot {
        return FaresActivityAssertionRobot(testSuite).apply(assertion)
    }

    private fun launchWithPrecondition(beforeLaunch: () -> Unit, intent: Intent) {
        beforeLaunch()
        launch(intent)
    }

    private fun launch(intent: Intent) {
        ActivityScenario.launch<FaresActivity>(intent)
    }

    private fun getIntentWithOriginAndDestination(): Intent {
        return FaresActivity.getIntent(
            context = context,
            origin = stubUiStation(),
            destination = stubUiStation(),
            busAndTramJourneyCount = 0
        )
    }

    private fun getIntentWithBusAndTramJourneyCount(): Intent {
        return FaresActivity.getIntent(
            context = context,
            origin = null,
            destination = null,
            busAndTramJourneyCount = 2
        )
    }
}
