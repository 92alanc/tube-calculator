package com.alancamargo.tubecalculator.search.ui

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.search.ui.robots.given
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
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

    @Inject
    lateinit var mockPreferencesManager: PreferencesManager

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun onFirstAccess_shouldShowFirstAccessDialogue() {
        given {
            launchOnFirstAccess()
        } then {
            showFirstAccessDialogue()
        }
    }

    @Test
    fun afterFirstAccess_shouldNotShowFirstAccessDialogue() {
        given {
            launchAfterFirstAccess()
        } then {
            doNotShowFirstAccessDialogue()
        }
    }

    @Test
    fun onLaunch_shouldLoadBannerAds() {
        given {
            launchAfterFirstAccess()
        } then {
            loadBannerAds()
        }
    }
}
