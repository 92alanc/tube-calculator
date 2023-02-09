package com.alancamargo.tubecalculator.core.test.ui

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf

fun performClick(@IdRes viewId: Int) {
    onView(
        allOf(
            withId(viewId),
            isDisplayed()
        )
    ).perform(click())
}
