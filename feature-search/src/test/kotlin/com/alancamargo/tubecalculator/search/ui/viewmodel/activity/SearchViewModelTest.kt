package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.testtools.stubBusAndTramJourney
import com.alancamargo.tubecalculator.search.testtools.stubRailJourney
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val mockAnalytics = mockk<SearchAnalytics>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val uiDelay = 0L
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = SearchViewModel(
        mockAnalytics,
        mockLogger,
        uiDelay,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.action,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `with rail journey on first launch onCreate should send AttachPreFilledRailJourneyFragments action`() {
        collector.test { _, actions ->
            // GIVEN
            val journey = stubRailJourney()

            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey,
                JourneyType.RAIL
            )

            // THEN
            val expected = SearchViewAction.AttachPreFilledRailJourneyFragments(journey)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with bus and tram journey on first launch onCreate should send AttachPreFilledBusAndTramJourneyFragment action`() {
        collector.test { _, actions ->
            // GIVEN
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey,
                JourneyType.BUS_AND_TRAM
            )

            // THEN
            val expected = SearchViewAction.AttachPreFilledBusAndTramJourneyFragment(journey)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with rail journey type on first launch onCreate should send AttachBlankRailJourneyFragments action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey = null,
                JourneyType.RAIL
            )

            // THEN
            val expected = SearchViewAction.AttachBlankRailJourneyFragments
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `with bus and tram journey type on first launch onCreate should send AttachBlankBusAndTramJourneyFragment action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey = null,
                JourneyType.BUS_AND_TRAM
            )

            // THEN
            val expected = SearchViewAction.AttachBlankBusAndTramJourneyFragment
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `on first launch onCreate should track screen view event`() {
        // WHEN
        viewModel.onCreate(
            isFirstLaunch = true,
            journey = null,
            journeyType = JourneyType.RAIL
        )

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `when not on first launch onCreate should not send any action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(
                isFirstLaunch = false,
                journey = null,
                journeyType = JourneyType.RAIL
            )

            // THEN
            assertThat(actions).isEmpty()
        }
    }

    @Test
    fun `when not on first launch onCreate should not track screen view event`() {
        // WHEN
        viewModel.onCreate(
            isFirstLaunch = false,
            journey = null,
            journeyType = JourneyType.RAIL
        )

        // THEN
        verify(exactly = 0) { mockAnalytics.trackScreenViewed() }
    }
}
