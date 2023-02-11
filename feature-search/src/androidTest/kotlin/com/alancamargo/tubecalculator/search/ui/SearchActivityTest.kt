package com.alancamargo.tubecalculator.search.ui

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.di.AppVersionName
import com.alancamargo.tubecalculator.core.preferences.PreferencesManager
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.test.ui.clickDropDownItem
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.search.data.database.SearchDao
import com.alancamargo.tubecalculator.search.ui.robots.given
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import org.junit.Before
import org.junit.Ignore
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

    @Inject
    lateinit var mockSearchDao: SearchDao

    @Inject
    @AppVersionName
    lateinit var appVersionName: String

    @Inject
    lateinit var mockRemoteConfigManager: RemoteConfigManager

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        every { mockRemoteConfigManager.getInt(key = "min_query_length") } returns 3
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
    fun whenClickCalculate_shouldShowMissingOriginOrDestinationErrorDialogue() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickCalculate()
        } then {
            showMissingOriginOrDestinationErrorDialogue()
        }
    }

    @Test
    fun withValidBusAndTramJourneyCount_whenClickCalculate_shouldNavigateToFares() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickAddBusAndTramJourney()
            clickCalculate()
        } then {
            navigateToFares()
        }
    }

    @Test(timeout = 5000)
    @Ignore("Dropdown suggestions not popping up with mock DAO")
    fun withSameOriginAndDestination_whenClickCalculate_shouldShowSameOriginAndDestinationErrorDialogue() {
        val query = "Hai"
        val stationName = "Hainault"

        given {
            withSearchResult(query, stationName)
            launchAfterFirstAccess()
        } withAction {
            typeOrigin(query)
            clickDropDownItem(stationName)
            typeDestination(query)
            clickDropDownItem(stationName)
            clickCalculate()
        } then {
            showSameOriginAndDestinationErrorDialogue()
        }
    }

    @Test(timeout = 5000)
    @Ignore("Dropdown suggestions not popping up with mock DAO")
    fun withDifferentOriginAndDestination_whenClickCalculate_shouldNavigateToFares() {
        val originQuery = "Rom"
        val origin = "Romford"
        val destinationQuery = "Far"
        val destination = "Farringdon"

        given {
            withSearchResult(originQuery, origin)
            withSearchResult(destinationQuery, destination)
            launchAfterFirstAccess()
        } withAction {
            typeOrigin(originQuery)
            clickDropDownItem(origin)
            typeDestination(destinationQuery)
            clickDropDownItem(destination)
            clickCalculate()
        } then {
            navigateToFares()
        }
    }

    @Test
    fun withGenericError_whenTypeQuery_shouldShowGenericErrorDialogue() {
        given {
            withGenericError()
            launchAfterFirstAccess()
        } withAction {
            typeOrigin(origin = "Knightsbridge")
        } then {
            showGenericErrorDialogue()
        }
    }

    @Test
    fun whenClickMoreInfo_shouldShowMoreInfoDialogue() {
        given {
            launchAfterFirstAccess()
        } withAction {
            clickMoreInfo()
        } then {
            showMoreInfoDialogue()
        }
    }
}
