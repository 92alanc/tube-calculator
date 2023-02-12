package com.alancamargo.tubecalculator.home.ui.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alancamargo.tubecalculator.core.test.ui.performClick
import com.alancamargo.tubecalculator.home.R
import com.alancamargo.tubecalculator.home.ui.HomeActivityTest

internal class HomeActivityActionRobot(private val testSuite: HomeActivityTest) {

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

    fun clickCalculate() {
        performClick(R.id.btCalculate)
    }

    infix fun then(
        assertion: HomeActivityAssertionRobot.() -> Unit
    ): HomeActivityAssertionRobot {
        return HomeActivityAssertionRobot(testSuite).apply(assertion)
    }

    private fun openDrawer() {
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
    }
}
