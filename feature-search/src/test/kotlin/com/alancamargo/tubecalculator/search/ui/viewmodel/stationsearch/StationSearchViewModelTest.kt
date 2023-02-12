package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.usecase.GetMinQueryLengthUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.SearchStationUseCase
import com.alancamargo.tubecalculator.search.testtools.*
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StationSearchViewModelTest {

    private val mockSearchStationUseCase = mockk<SearchStationUseCase>()
    private val mockGetMinQueryLengthUseCase = mockk<GetMinQueryLengthUseCase>()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = StationSearchViewModel(
        mockSearchStationUseCase,
        mockGetMinQueryLengthUseCase,
        mockLogger,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.state,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Test
    fun `onCreate should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockGetMinQueryLengthUseCase() } returns MIN_QUERY_LENGTH
            val searchType = SearchType.ORIGIN
            val station = stubUiStation()

            // WHEN
            viewModel.onCreate(searchType, station)

            // THEN
            val expected = StationSearchViewState(
                minQueryLength = MIN_QUERY_LENGTH,
                selectedStation = station,
                labelRes = searchType.labelRes,
                hintRes = searchType.hintRes
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when query is blank onQueryChanged should not search station`() {
        // WHEN
        viewModel.onQueryChanged(query = "")

        // THEN
        verify(exactly = 0) { mockSearchStationUseCase(query = any()) }
    }

    @Test
    fun `when use case returns Success onQueryChanged should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns stubSuccessfulStationListResultFlow()

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val stations = stubUiStationList()
            val expected = StationSearchViewState(stations = stations)
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when use case returns GenericError onQueryChanged should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws exception onQueryChanged should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flow { throw Throwable() }

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws exception onQueryChanged should log exception`() {
        collector.test { _, _ ->
            // GIVEN
            val exception = Throwable()
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flow { throw exception }

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            verify { mockLogger.error(exception) }
        }
    }
}
