package com.alancamargo.tubecalculator.home.ui

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.home.ui.robots.given
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class HomeActivityTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockDialogueHelper: DialogueHelper

    @Inject
    lateinit var mockAdLoader: AdLoader

    @Inject
    lateinit var mockPreferencesManager: PreferencesManager

    @Inject
    lateinit var mockSettingsActivityNavigation: SettingsActivityNavigation

    @Inject
    @AppVersionName
    lateinit var appVersionName: String

    @Inject
    lateinit var mockSearchActivityNavigation: SearchActivityNavigation

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

    @Test
    fun whenClickSettings_shouldNavigateToSettings() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickSettings()
        } then {
            navigateToSettings()
        }
    }

    @Test
    fun whenClickPrivacy_shouldShowPrivacyPolicyDialogue() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickPrivacy()
        } then {
            showPrivacyPolicyDialogue()
        }
    }

    @Test
    fun whenClickAbout_shouldShowAppInfoDialogue() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickAbout()
        } then {
            showAppInfoDialogue()
        }
    }

    @Test
    fun whenClickAddJourney_shouldShowAddRailJourneyButton() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickAddJourney()
        } then {
            showAddRailJourneyButton()
        }
    }

    @Test
    fun whenClickAddJourney_shouldShowAddBusAndTramJourneyButton() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickAddJourney()
        } then {
            showAddBusAndTramJourneyButton()
        }
    }

    @Test
    fun whenClickAddRailJourney_shouldNavigateToSearchToCreateJourney() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickAddJourney()
            clickAddRailJourney()
        } then {
            navigateToSearchToCreateJourney()
        }
    }

    @Test
    fun whenClickAddBusAndTramJourney_shouldNavigateToSearchToCreateJourney() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickAddJourney()
            clickAddBusAndTramJourney()
        } then {
            navigateToSearchToCreateJourney()
        }
    }
}
