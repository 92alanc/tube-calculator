package com.alancamargo.tubecalculator.fares.ui.robots

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alancamargo.tubecalculator.core.test.ui.assertViewIsDisplayed
import com.alancamargo.tubecalculator.core.test.ui.withRecyclerViewItemCount
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.FaresActivityTest
import io.mockk.verify
import com.alancamargo.tubecalculator.core.design.R as R2

internal class FaresActivityAssertionRobot(private val testSuite: FaresActivityTest) {

    fun loadBannerAds() {
        verify { testSuite.mockAdLoader.loadBannerAds(target = any()) }
    }

    fun loadInterstitialAds() {
        verify {
            testSuite.mockAdLoader.loadInterstitialAds(
                activity = any(),
                adIdRes = R.string.ads_interstitial_fares
            )
        }
    }

    fun navigateToHome() {
        verify { testSuite.mockHomeActivityNavigation.startActivity(context = any()) }
    }

    fun recyclerViewHasCorrectItemCount() {
        onView(withId(R.id.rootRecyclerView)).check(withRecyclerViewItemCount(2))
    }

    fun shimmerIsDisplayed() {
        assertViewIsDisplayed(R.id.shimmerContainer)
    }

    fun networkErrorDialogueIsDisplayed() {
        dialogueIsDisplayed(
            titleRes = R2.string.error,
            messageRes = R2.string.message_network_error
        )
    }

    fun invalidQueryErrorDialogueIsDisplayed() {
        dialogueIsDisplayed(
            titleRes = R2.string.error,
            messageRes = R.string.fares_message_invalid_query_error
        )
    }

    fun genericErrorDialogueIsDisplayed() {
        dialogueIsDisplayed(
            titleRes = R2.string.error,
            messageRes = R2.string.message_generic_error
        )
    }

    fun messagesDialogueIsDisplayed() {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R.string.fares_messages,
                message = any()
            )
        }
    }

    private fun dialogueIsDisplayed(
        @StringRes titleRes: Int,
        @StringRes messageRes: Int
    ) {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = titleRes,
                messageRes = messageRes,
                onDismiss = any()
            )
        }
    }
}
