package com.alancamargo.tubecalculator.fares.ui

import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.fares.data.database.RailFaresDao
import com.alancamargo.tubecalculator.fares.data.model.database.RemoteDatabaseFareListWrapper
import com.alancamargo.tubecalculator.fares.ui.robots.given
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class FaresActivityTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockDialogueHelper: DialogueHelper

    @Inject
    lateinit var mockAdLoader: AdLoader

    @Inject
    lateinit var mockSearchActivityNavigation: SearchActivityNavigation

    @Inject
    lateinit var mockRemoteConfigManager: RemoteConfigManager

    @Inject
    lateinit var mockRailFaresDao: RailFaresDao

    @Inject
    lateinit var mockRemoteDatabase: RemoteDatabase

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        every { mockRemoteConfigManager.getDouble(key = any()) } returns 1.65
        coEvery {
            mockRailFaresDao.getRailFares(
                originId = any(),
                destinationId = any()
            )
        } returns null
        coEvery {
            mockRemoteDatabase.load(
                collectionName = any(),
                documentId = any(),
                outputClass = RemoteDatabaseFareListWrapper::class.java
            )
        } returns null
    }

    @Test
    fun onLaunch_shouldLoadBannerAds() {
        given {
            launchWithBusAndTramFaresOnly()
        } then {
            loadBannerAds()
        }
    }

    @Test
    fun onLaunch_shouldLoadInterstitialAds() {
        given {
            launchWithBusAndTramFaresOnly()
        } then {
            loadInterstitialAds()
        }
    }

    @Test
    fun whenClickNewSearch_shouldNavigateToSearch() {
        given {
            launchWithBusAndTramFaresOnly()
        } withAction {
            clickNewSearch()
        } then {
            navigateToSearch()
        }
    }

    @Test
    fun withSuccess_recyclerViewShouldHaveCorrectItemCount() {
        given {
            launchWithSuccess()
        } then {
            recyclerViewHasCorrectItemCount()
        }
    }

    @Test
    fun whenLoading_shouldShowShimmer() {
        given {
            launchWithDelayedWebResponse()
        } then {
            shimmerIsDisplayed()
        }
    }

    @Test
    fun whenDisconnected_shouldShowNetworkErrorDialogue() {
        given {
            launchDisconnected()
        } then {
            networkErrorDialogueIsDisplayed()
        }
    }

    @Test
    fun withInvalidQueryError_shouldShowInvalidQueryErrorDialogue() {
        given {
            launchWithInvalidQueryError()
        } then {
            invalidQueryErrorDialogueIsDisplayed()
        }
    }

    @Test
    fun withUnknownError_shouldShowGenericErrorDialogue() {
        given {
            launchWithUnknownError()
        } then {
            genericErrorDialogueIsDisplayed()
        }
    }

    @Test
    fun whenClickingOnMessagesButton_shouldShowMessagesDialogue() {
        given {
            launchWithSuccess()
        } withAction {
            clickMessages()
        } then {
            messagesDialogueIsDisplayed()
        }
    }
}
