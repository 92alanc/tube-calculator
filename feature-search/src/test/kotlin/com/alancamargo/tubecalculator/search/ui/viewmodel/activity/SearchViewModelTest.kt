package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.search.testtools.stubUiStation
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val mockIsFirstAccessUseCase = mockk<IsFirstAccessUseCase>()
    private val mockDisableFirstAccessUseCase = mockk<DisableFirstAccessUseCase>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()
    private val viewModel = SearchViewModel(
        mockIsFirstAccessUseCase,
        mockDisableFirstAccessUseCase,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.action,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `on first access onStart should send ShowFirstAccessDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockIsFirstAccessUseCase() } returns true

            // WHEN
            viewModel.onStart()

            // THEN
            delay(200)
            assertThat(actions).contains(SearchViewAction.ShowFirstAccessDialogue)
        }
    }

    @Test
    fun `when not on first access onStart should not send ShowFirstAccessDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockIsFirstAccessUseCase() } returns false

            // WHEN
            viewModel.onStart()

            // THEN
            assertThat(actions).doesNotContain(SearchViewAction.ShowFirstAccessDialogue)
        }
    }

    @Test
    fun `onFirstAccessDialogueDismissed should disable first access`() {
        // WHEN
        viewModel.onFirstAccessDialogueDismissed()

        // THEN
        verify { mockDisableFirstAccessUseCase() }
    }

    @Test
    fun `onFirstAccessDialogueDismissed should send NavigateToSettings action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onFirstAccessDialogueDismissed()

            // THEN
            assertThat(actions).contains(SearchViewAction.NavigateToSettings)
        }
    }

    @Test
    fun `onSettingsClicked should send NavigateToSettings action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onSettingsClicked()

            // THEN
            assertThat(actions).contains(SearchViewAction.NavigateToSettings)
        }
    }

    @Test
    fun `onAppInfoClicked should send ShowAppInfo action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onAppInfoClicked()

            // THEN
            assertThat(actions).contains(SearchViewAction.ShowAppInfo)
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
