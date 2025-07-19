package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import app.cash.turbine.test
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.testtools.stubBusAndTramJourney
import com.alancamargo.tubecalculator.search.testtools.stubRailJourney
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val mockAnalytics = mockk<SearchAnalytics>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val uiDelay = 0L
    private val dispatcher = StandardTestDispatcher()

    private val viewModel = SearchViewModel(
        mockAnalytics,
        mockLogger,
        uiDelay,
        dispatcher
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `with rail journey on first launch onCreate should send AttachPreFilledRailJourneyFragments action`() {
        runTest {
            // GIVEN
            val journey = stubRailJourney()

            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey,
                JourneyType.RAIL
            )

            // THEN
            viewModel.action.test {
                val expected = SearchViewAction.AttachPreFilledRailJourneyFragments(journey)
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `with bus and tram journey on first launch onCreate should send AttachPreFilledBusAndTramJourneyFragment action`() {
        runTest {
            // GIVEN
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey,
                JourneyType.BUS_AND_TRAM
            )

            // THEN
            viewModel.action.test {
                val expected = SearchViewAction.AttachPreFilledBusAndTramJourneyFragment(journey)
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `with rail journey type on first launch onCreate should send AttachBlankRailJourneyFragments action`() {
        runTest {
            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey = null,
                JourneyType.RAIL
            )

            // THEN
            viewModel.action.test {
                val expected = SearchViewAction.AttachBlankRailJourneyFragments
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `with bus and tram journey type on first launch onCreate should send AttachBlankBusAndTramJourneyFragment action`() {
        runTest {
            // WHEN
            viewModel.onCreate(
                isFirstLaunch = true,
                journey = null,
                JourneyType.BUS_AND_TRAM
            )

            // THEN
            viewModel.action.test {
                val expected = SearchViewAction.AttachBlankBusAndTramJourneyFragment
                assertThat(awaitItem()).isEqualTo(expected)
            }
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
