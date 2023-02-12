package com.alancamargo.tubecalculator.home.ui.robots

import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.core.test.ui.assertViewIsDisplayed
import com.alancamargo.tubecalculator.home.R
import com.alancamargo.tubecalculator.home.ui.HomeActivityTest
import io.mockk.verify
import com.alancamargo.tubecalculator.core.design.R as R2

internal class HomeActivityAssertionRobot(private val testSuite: HomeActivityTest) {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun loadBannerAds() {
        verify { testSuite.mockAdLoader.loadBannerAds(target = any()) }
    }

    fun navigateToSettings() {
        verify { testSuite.mockSettingsActivityNavigation.startActivity(context = any()) }
    }

    fun navigateToSearchToCreateJourney() {
        verify {
            testSuite.mockSearchActivityNavigation.startActivityForResult(
                context = any(),
                launcher = any(),
                journeyType = any(),
                onResult = any()
            )
        }
    }

    fun showFirstAccessDialogue() {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R.string.first_access_title,
                messageRes = R.string.first_access_message,
                positiveButtonTextRes = R2.string.settings,
                onPositiveButtonClick = any(),
                negativeButtonTextRes = R.string.not_now,
                onNegativeButtonClick = any()
            )
        }
    }

    fun doNotShowFirstAccessDialogue() {
        verify(exactly = 0) {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R.string.first_access_title,
                messageRes = R.string.first_access_message,
                buttonTextRes = R2.string.settings
            )
        }
    }

    fun showPrivacyPolicyDialogue() {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R2.string.privacy_policy,
                messageRes = R2.string.privacy_policy_content
            )
        }
    }

    fun showAppInfoDialogue() {
        val appName = context.getString(R2.string.app_name)

        val appNameAndVersion = context.getString(
            R2.string.app_name_and_version_format,
            appName,
            testSuite.appVersionName
        )

        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                iconRes = R2.mipmap.ic_launcher_round,
                title = appNameAndVersion,
                messageRes = R.string.home_app_info
            )
        }
    }

    fun showAddRailJourneyButton() {
        assertViewIsDisplayed(R.id.btAddRailJourney)
    }

    fun showAddBusAndTramJourneyButton() {
        assertViewIsDisplayed(R.id.btAddBusAndTramJourney)
    }
}
