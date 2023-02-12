package com.alancamargo.tubecalculator.fares.ui.robots

import com.alancamargo.tubecalculator.core.test.ui.performClick
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.FaresActivityTest

internal class FaresActivityActionRobot(private val testSuite: FaresActivityTest) {

    fun clickNewSearch() {
        performClick(R.id.btNewSearch)
    }

    fun clickMessages() {
        performClick(R.id.btMessages)
    }

    infix fun then(assertion: FaresActivityAssertionRobot.() -> Unit): FaresActivityAssertionRobot {
        return FaresActivityAssertionRobot(testSuite).apply(assertion)
    }
}
