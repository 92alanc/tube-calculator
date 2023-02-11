package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.data.analytics.SearchAnalytics
import com.alancamargo.tubecalculator.search.testtools.stubUiStation
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val mockAnalytics = mockk<SearchAnalytics>(relaxed = true)
    private val uiDelay = 0L
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = SearchViewModel(
        mockAnalytics,
        uiDelay,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.action,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `on first launch onCreate should send AttachFragments action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(isFirstLaunch = true)

            // THEN
            val expected = SearchViewAction.AttachFragments
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `on first launch onCreate should track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = true)

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `when not on first launch onCreate should not send AttachFragments action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onCreate(isFirstLaunch = false)

            // THEN
            val expected = SearchViewAction.AttachFragments
            assertThat(actions).doesNotContain(expected)
        }
    }

    @Test
    fun `when not on first launch onCreate should not track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = false)

        // THEN
        verify(exactly = 0) { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `onCalculateClicked should track button click event`() {
        // WHEN
        val origin = "Romford"
        val destination = "Blackfriars"
        val busAndTramJourneyCount = 1
        viewModel.onCalculateClicked(
            origin = stubUiStation(origin),
            destination = stubUiStation(destination),
            busAndTramJourneyCount = busAndTramJourneyCount
        )

        // THEN
        verify {
            mockAnalytics.trackCalculateClicked(
                origin,
                destination,
                busAndTramJourneyCount
            )
        }
    }

    @Test
    fun `onCalculateClicked should send NavigateToFares action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")
            val destination = stubUiStation(name = "Westminster")
            val busAndTramJourneyCount = 3

            // WHEN
            viewModel.onCalculateClicked(origin, destination, busAndTramJourneyCount)

            // THEN
            val expected = SearchViewAction.NavigateToFares(
                origin = origin,
                destination = destination,
                busAndTramJourneyCount = busAndTramJourneyCount
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when origin is null onCalculateClicked should send NavigateToFares action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")
            val busAndTramJourneyCount = 1

            // WHEN
            viewModel.onCalculateClicked(
                origin = origin,
                destination = null,
                busAndTramJourneyCount = busAndTramJourneyCount
            )

            // THEN
            val expected = SearchViewAction.NavigateToFares(
                origin = origin,
                destination = null,
                busAndTramJourneyCount = busAndTramJourneyCount
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when destination is null onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val destination = stubUiStation(name = "Romford")
            val busAndTramJourneyCount = 1

            // WHEN
            viewModel.onCalculateClicked(
                origin = null,
                destination = destination,
                busAndTramJourneyCount = busAndTramJourneyCount
            )

            // THEN
            val expected = SearchViewAction.NavigateToFares(
                origin = null,
                destination = destination,
                busAndTramJourneyCount = busAndTramJourneyCount
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when origin is null and bus and tram journey count is zero onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val destination = stubUiStation(name = "Westminster")

            // WHEN
            viewModel.onCalculateClicked(
                origin = null,
                destination = destination,
                busAndTramJourneyCount = 0
            )

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.MISSING_ORIGIN_OR_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when destination is null and bus and tram journey count is zero onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")

            // WHEN
            viewModel.onCalculateClicked(
                origin = origin,
                destination = null,
                busAndTramJourneyCount = 0
            )

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.MISSING_ORIGIN_OR_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when origin and destination are the same onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")

            // WHEN
            viewModel.onCalculateClicked(
                origin = origin,
                destination = origin,
                busAndTramJourneyCount = 0
            )

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.SAME_ORIGIN_AND_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }
}
