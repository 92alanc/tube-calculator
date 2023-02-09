package com.alancamargo.tubecalculator.core.test.ui

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

fun assertViewIsDisplayed(@IdRes viewId: Int) {
    onView(withId(viewId)).check(matches(isDisplayed()))
}
