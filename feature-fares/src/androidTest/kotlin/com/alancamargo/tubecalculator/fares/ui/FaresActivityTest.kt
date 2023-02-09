package com.alancamargo.tubecalculator.fares.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import com.alancamargo.tubecalculator.core.test.web.mockWebResponse
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.testtools.stubUiStation
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.verify
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

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        every { mockRemoteConfigManager.getDouble(key = any()) } returns 1.65
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
    fun whenServiceReturnsSuccess_shouldDisplayFares() {
        mockWebResponse(jsonAssetPath = "fares_success.json")

        val intent = FaresActivity.getIntent(
            context = context,
            origin = stubUiStation(),
            destination = stubUiStation(),
            busAndTramJourneyCount = 0
        )
        ActivityScenario.launch<FaresActivity>(intent)

        onView(withText("Romford Rail Station")).check(matches(isDisplayed()))
    }
}
