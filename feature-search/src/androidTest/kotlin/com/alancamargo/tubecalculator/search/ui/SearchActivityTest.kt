package com.alancamargo.tubecalculator.search.ui

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.test.ui.clickDropDownItem
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
    lateinit var mockSearchDao: SearchDao

    @Inject
    lateinit var mockRemoteConfigManager: RemoteConfigManager

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        every { mockRemoteConfigManager.getInt(key = "min_query_length") } returns 3
    }

    @Test
    fun onLaunch_shouldLoadBannerAds() {
        given {
            launchWithBlankRailJourney()
        } then {
            loadBannerAds()
        }
    }

    @Test
    fun withValidBusAndTramJourneyCount_whenClickNext_shouldSendJourneyResult() {
        given {
            launchWithBlankBusAndTramJourney()
        } withAction {
            clickAddBusAndTramJourney()
            clickNext()
        } then {
            sendJourneyResult()
        }
    }

    @Test(timeout = 5000)
    @Ignore("Dropdown suggestions not popping up with mock DAO")
    fun withSameOriginAndDestination_whenClickNext_shouldShowSameOriginAndDestinationErrorDialogue() {
        val query = "Hai"
        val stationName = "Hainault"

        given {
            withSearchResult(query, stationName)
            launchWithBlankRailJourney()
        } withAction {
            typeOrigin(query)
            clickDropDownItem(stationName)
            typeDestination(query)
            clickDropDownItem(stationName)
            clickNext()
        } then {
            showSameOriginAndDestinationErrorDialogue()
        }
    }

    @Test(timeout = 5000)
    @Ignore("Dropdown suggestions not popping up with mock DAO")
    fun withDifferentOriginAndDestination_whenClickNext_shouldSendJourneyResult() {
        val originQuery = "Rom"
        val origin = "Romford"
        val destinationQuery = "Far"
        val destination = "Farringdon"

        given {
            withSearchResult(originQuery, origin)
            withSearchResult(destinationQuery, destination)
            launchWithBlankRailJourney()
        } withAction {
            typeOrigin(originQuery)
            clickDropDownItem(origin)
            typeDestination(destinationQuery)
            clickDropDownItem(destination)
            clickNext()
        } then {
            sendJourneyResult()
        }
    }

    @Test
    fun withGenericError_whenTypeQuery_shouldShowGenericErrorDialogue() {
        given {
            withGenericError()
            launchWithBlankRailJourney()
        } withAction {
            typeOrigin(origin = "Knightsbridge")
        } then {
            showGenericErrorDialogue()
        }
    }

    @Test
    fun whenClickMoreInfo_shouldShowMoreInfoDialogue() {
        given {
            launchWithBlankBusAndTramJourney()
        } withAction {
            clickMoreInfo()
        } then {
            showMoreInfoDialogue()
        }
    }
}
