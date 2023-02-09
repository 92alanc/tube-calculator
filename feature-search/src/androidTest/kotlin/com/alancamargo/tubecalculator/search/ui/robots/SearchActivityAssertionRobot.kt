package com.alancamargo.tubecalculator.search.ui.robots

import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import com.alancamargo.tubecalculator.core.extensions.getVersionName
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.alancamargo.tubecalculator.core.design.R as R2

internal class SearchActivityAssertionRobot(private val testSuite: SearchActivityTest) {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun loadBannerAds() {
        verify { testSuite.mockAdLoader.loadBannerAds(target = any()) }
    }

    fun navigateToSettings() {
        verify { testSuite.mockSettingsActivityNavigation.startActivity(context) }
    }

    fun navigateToFares() {
        verify {
            testSuite.mockFaresActivityNavigation.startActivity(
                context = context,
                origin = any(),
                destination = any(),
                busAndTramJourneyCount = any()
            )
        }
    }

    fun showFirstAccessDialogue() {
        runBlocking { delay(200) }
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R.string.first_access_title,
                messageRes = R.string.first_access_message,
                buttonTextRes = R.string.sounds_good,
                onDismiss = any()
            )
        }
    }

    fun doNotShowFirstAccessDialogue() {
        verify(exactly = 0) {
            testSuite.mockDialogueHelper.showDialogue(
                context = context,
                titleRes = R.string.first_access_title,
                messageRes = R.string.first_access_message,
                buttonTextRes = R.string.sounds_good
            )
        }
    }

    fun showPrivacyPolicyDialogue() {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = context,
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
            context.getVersionName()
        )

        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = context,
                iconRes = R2.mipmap.ic_launcher_round,
                title = appNameAndVersion,
                messageRes = R.string.search_app_info
            )
        }
    }

    fun showMissingOriginOrDestinationErrorDialogue() {
        showErrorDialogue(R.string.search_error_missing_origin_destination)
    }

    fun showSameOriginAndDestinationErrorDialogue() {
        showErrorDialogue(R.string.search_error_same_origin_destination)
    }

    fun showGenericErrorDialogue() {
        showErrorDialogue(R2.string.message_generic_error)
    }

    private fun showErrorDialogue(@StringRes messageRes: Int) {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = context,
                titleRes = R2.string.error,
                messageRes = messageRes
            )
        }
    }
}
