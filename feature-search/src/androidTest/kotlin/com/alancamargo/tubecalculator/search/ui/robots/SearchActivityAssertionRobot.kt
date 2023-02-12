package com.alancamargo.tubecalculator.search.ui.robots

import androidx.annotation.StringRes
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.ui.SearchActivityTest
import io.mockk.verify
import com.alancamargo.tubecalculator.core.design.R as R2

internal class SearchActivityAssertionRobot(private val testSuite: SearchActivityTest) {

    fun loadBannerAds() {
        verify { testSuite.mockAdLoader.loadBannerAds(target = any()) }
    }

    fun sendJourneyResult() {
        // TODO
    }

    fun showSameOriginAndDestinationErrorDialogue() {
        showErrorDialogue(R.string.search_error_same_origin_destination)
    }

    fun showGenericErrorDialogue() {
        showErrorDialogue(R2.string.message_generic_error)
    }

    fun showMoreInfoDialogue() {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R.string.bus_tram_journeys,
                messageRes = R.string.search_bus_tram_journeys_info
            )
        }
    }

    private fun showErrorDialogue(@StringRes messageRes: Int) {
        verify {
            testSuite.mockDialogueHelper.showDialogue(
                context = any(),
                titleRes = R2.string.error,
                messageRes = messageRes
            )
        }
    }
}
