package com.alancamargo.tubecalculator.settings.ui

import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.settings.ui.robots.given
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class SettingsActivityTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockAdLoader: AdLoader

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun onLaunch_shouldLoadBannerAds() {
        given {
            launch()
        } then {
            loadBannerAds()
        }
    }

    @Test
    fun onLaunch_analyticsShouldBeEnabled() {
        given {
            launch()
        } then {
            analyticsIsEnabled()
        }
    }

    @Test
    fun onLaunch_crashLoggingShouldBeEnabled() {
        given {
            launch()
        } then {
            crashLoggingIsEnabled()
        }
    }

    @Test
    fun onLaunch_personalisedAdsShouldBeDisabled() {
        given {
            launch()
        } then {
            personalisedAdsAreDisabled()
        }
    }
}
