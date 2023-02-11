package com.alancamargo.tubecalculator.core.test.ui

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString

fun performClick(@IdRes viewId: Int) {
    onView(
        allOf(
            withId(viewId),
            isDisplayed()
        )
    ).perform(click())
}

fun clickDropDownItem(text: String) {
    onView(
        withText(containsString(text))
    ).inRoot(
        isPlatformPopup()
    ).perform(click())
}
