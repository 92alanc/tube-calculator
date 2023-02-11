package com.alancamargo.tubecalculator.search.ui.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alancamargo.tubecalculator.core.test.ui.performClick
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest

internal class SearchActivityActionRobot(private val testSuite: SearchActivityTest) {

    fun clickSettings() {
        openDrawer()
        performClick(R.id.itemSettings)
    }

    fun clickPrivacy() {
        openDrawer()
        performClick(R.id.itemPrivacy)
    }

    fun clickAbout() {
        openDrawer()
        performClick(R.id.itemAbout)
    }

    fun clickAddBusAndTramJourney() {
        performClick(R.id.btUp)
    }

    fun clickCalculate() {
        performClick(R.id.btCalculate)
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

    private fun openDrawer() {
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    }
}
