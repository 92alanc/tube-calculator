package com.alancamargo.tubecalculator.fares.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.core.database.remote.RemoteDatabase
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.test.assertions.withRecyclerViewItemCount
import com.alancamargo.tubecalculator.core.test.web.delayWebResponse
import com.alancamargo.tubecalculator.core.test.web.disconnect
import com.alancamargo.tubecalculator.core.test.web.mockWebResponse
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.data.database.RailFaresDao
import com.alancamargo.tubecalculator.fares.data.model.database.RemoteDatabaseFareListWrapper
import com.alancamargo.tubecalculator.fares.testtools.stubUiStation
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.alancamargo.tubecalculator.core.design.R as R2

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

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

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
        val intent = FaresActivity.getIntent(
            context = context,
            origin = null,
            destination = null,
            busAndTramJourneyCount = 2
        )
        ActivityScenario.launch<FaresActivity>(intent)

        verify { mockAdLoader.loadBannerAds(target = any()) }
    }

    @Test
    fun onLaunch_shouldLoadInterstitialAds() {
        val intent = FaresActivity.getIntent(
            context = context,
            origin = null,
            destination = null,
            busAndTramJourneyCount = 2
        )

        ActivityScenario.launch<FaresActivity>(intent).onActivity {
            verify {
                mockAdLoader.loadInterstitialAds(
                    activity = it,
                    adIdRes = R.string.ads_interstitial_fares
                )
            }
        }
    }

    @Test
    fun whenClickNewSearch_shouldNavigateToSearch() {
        val intent = FaresActivity.getIntent(
            context = context,
            origin = null,
            destination = null,
            busAndTramJourneyCount = 2
        )
        ActivityScenario.launch<FaresActivity>(intent)

        onView(withId(R.id.btNewSearch)).perform(click())

        verify { mockSearchActivityNavigation.startActivity(context = any()) }
    }

    @Test
    fun whenServiceReturnsSuccess_recyclerViewShouldHaveCorrectItemCount() {
        mockWebResponse(jsonAssetPath = "fares_success.json")

        val intent = FaresActivity.getIntent(
            context = context,
            origin = stubUiStation(),
            destination = stubUiStation(),
            busAndTramJourneyCount = 0
        )
        ActivityScenario.launch<FaresActivity>(intent)

        onView(withId(R.id.rootRecyclerView)).check(withRecyclerViewItemCount(2))
    }

    @Test
    fun whenLoading_shouldShowShimmer() {
        delayWebResponse()

        val intent = FaresActivity.getIntent(
            context = context,
            origin = stubUiStation(),
            destination = stubUiStation(),
            busAndTramJourneyCount = 0
        )
        ActivityScenario.launch<FaresActivity>(intent)

        onView(withId(R.id.shimmerContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun whenDisconnected_shouldShowNetworkErrorDialogue() {
        disconnect()

        val intent = FaresActivity.getIntent(
            context = context,
            origin = stubUiStation(),
            destination = stubUiStation(),
            busAndTramJourneyCount = 0
        )
        ActivityScenario.launch<FaresActivity>(intent)

        verify {
            mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R2.string.error,
                messageRes = R2.string.message_network_error,
                onDismiss = any()
            )
        }
    }
}
