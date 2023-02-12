package com.alancamargo.tubecalculator.search.ui.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withHint
import com.alancamargo.tubecalculator.core.test.ui.performClick
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest

internal class SearchActivityActionRobot(private val testSuite: SearchActivityTest) {

    fun clickAddBusAndTramJourney() {
        performClick(R.id.btUp)
    }

    fun clickNext() {
        performClick(R.id.btNext)
    }

    fun typeOrigin(origin: String) {
        onView(withHint(R.string.search_label_origin)).perform(typeText(origin))
    }

    fun typeDestination(destination: String) {
        onView(withHint(R.string.search_label_destination)).perform(typeText(destination))
    }

    fun clickMoreInfo() {
        performClick(R.id.btInfo)
    }

    infix fun then(
        assertion: SearchActivityAssertionRobot.() -> Unit
    ): SearchActivityAssertionRobot {
        return SearchActivityAssertionRobot(testSuite).apply(assertion)
    }
}
