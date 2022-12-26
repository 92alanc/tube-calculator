package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.testtools.stubUiStation
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()
    private val viewModel = SearchViewModel(dispatcher)

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.action,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

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
            viewModel.setOrigin(origin)

            val destination = stubUiStation(name = "Westminster")
            viewModel.setDestination(destination)

            val busAndTramJourneyCount = 3
            viewModel.setBusAndTramJourneyCount(busAndTramJourneyCount)

            // WHEN
            viewModel.onCalculateClicked()

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
    fun `when origin is null onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val destination = stubUiStation(name = "Westminster")
            viewModel.setDestination(destination)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.MISSING_ORIGIN_OR_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when destination is null onCalculateClicked should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val origin = stubUiStation(name = "Romford")
            viewModel.setOrigin(origin)

            // WHEN
            viewModel.onCalculateClicked()

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
            viewModel.setOrigin(origin)
            viewModel.setDestination(origin)

            // WHEN
            viewModel.onCalculateClicked()

            // THEN
            val expected = SearchViewAction.ShowErrorDialogue(
                UiSearchError.SAME_ORIGIN_AND_DESTINATION
            )
            assertThat(actions).contains(expected)
        }
    }
}
