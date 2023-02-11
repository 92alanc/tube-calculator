package com.alancamargo.tubecalculator.core.test.ui

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

fun assertViewIsDisplayed(@IdRes viewId: Int) {
    onView(withId(viewId)).check(matches(isDisplayed()))
}

fun assertViewIsChecked(@IdRes viewId: Int) {
    onView(withId(viewId)).check(matches(isChecked()))
}

fun assertViewIsNotChecked(@IdRes viewId: Int) {
    onView(withId(viewId)).check(matches(isNotChecked()))
}
