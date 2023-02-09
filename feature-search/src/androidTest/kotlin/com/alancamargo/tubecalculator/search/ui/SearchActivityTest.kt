package com.alancamargo.tubecalculator.search.ui

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

internal class SearchActivityTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockDialogueHelper: DialogueHelper

    @Inject
    lateinit var mockAdLoader: AdLoader

    @Inject
    lateinit var mockFaresActivityNavigation: FaresActivityNavigation

    @Inject
    lateinit var mockSettingsActivityNavigation: SettingsActivityNavigation

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }
}
