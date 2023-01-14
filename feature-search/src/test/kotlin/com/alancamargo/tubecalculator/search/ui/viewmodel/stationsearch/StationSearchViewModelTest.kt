package com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch

import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.search.domain.model.StationListResult
import com.alancamargo.tubecalculator.search.domain.usecase.GetMinQueryLengthUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.GetSearchTriggerDelayUseCase
import com.alancamargo.tubecalculator.search.domain.usecase.SearchStationUseCase
import com.alancamargo.tubecalculator.search.testtools.SEARCH_QUERY
import com.alancamargo.tubecalculator.search.testtools.stubSuccessfulSearchFlow
import com.alancamargo.tubecalculator.search.testtools.stubUiStationList
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class StationSearchViewModelTest {

    private val mockSearchStationUseCase = mockk<SearchStationUseCase>()
    private val mockGetMinQueryLengthUseCase = mockk<GetMinQueryLengthUseCase>()
    private val mockGetSearchTriggerDelayUseCase = mockk<GetSearchTriggerDelayUseCase>()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = StationSearchViewModel(
        mockSearchStationUseCase,
        mockGetMinQueryLengthUseCase,
        mockGetSearchTriggerDelayUseCase,
        mockLogger,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        stateFlow = viewModel.state,
        actionFlow = viewModel.action,
        dispatcher = dispatcher
    )

    @Before
    fun setUp() {
        every { mockGetMinQueryLengthUseCase() } returns 4
        every { mockGetSearchTriggerDelayUseCase() } returns 0
    }

    @Test
    fun `when query is empty onQueryChanged should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onQueryChanged(query = "")

            // THEN
            val expected = StationSearchViewState()
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when query is less than minimum query length onQueryChanged should not search station`() {
        // GIVEN
        every { mockGetMinQueryLengthUseCase() } returns 100

        // WHEN
        viewModel.onQueryChanged(SEARCH_QUERY)

        // THEN
        verify(exactly = 0) { mockSearchStationUseCase(query = any()) }
    }

    @Test
    fun `when query is not empty and station is not selected onQueryChanged should search station`() {
        // GIVEN
        every {
            mockSearchStationUseCase(SEARCH_QUERY)
        } returns flowOf(StationListResult.ServerError)

        // WHEN
        viewModel.onQueryChanged(SEARCH_QUERY)

        // THEN
        verify { mockSearchStationUseCase(SEARCH_QUERY) }
    }

    @Test
    fun `when use case returns Success onQueryChanged should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns stubSuccessfulSearchFlow()

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val stations = stubUiStationList()
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState(
                    isLoading = true,
                    searchResults = stations
                ),
                StationSearchViewState(searchResults = stations)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Success onQueryChanged should not log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns stubSuccessfulSearchFlow()

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            verify(exactly = 0) { mockLogger.debug(message = any()) }
        }
    }

    @Test
    fun `when use case returns Empty onQueryChanged should set correct states`() {
        collector.test { states, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flowOf(StationListResult.Empty)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState(
                    isLoading = true,
                    showEmptyState = true
                ),
                StationSearchViewState(showEmptyState = true)
            )
            assertThat(states).containsAtLeastElementsIn(expected)
        }
    }

    @Test
    fun `when use case returns Empty onQueryChanged should not log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every { mockSearchStationUseCase(SEARCH_QUERY) } returns flowOf(StationListResult.Empty)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            verify(exactly = 0) { mockLogger.debug(message = any()) }
        }
    }

    @Test
    fun `when use case returns NetworkError onQueryChanged should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.NetworkError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.NETWORK)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns NetworkError onQueryChanged should not log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.NetworkError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            verify(exactly = 0) { mockLogger.debug(message = any()) }
        }
    }

    @Test
    fun `when use case returns ServerError onQueryChanged should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.ServerError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.SERVER)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns ServerError onQueryChanged should log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.ServerError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val message = "Query: $SEARCH_QUERY. Result: ${StationListResult.ServerError}"
            verify { mockLogger.debug(message) }
        }
    }

    @Test
    fun `when use case returns GenericError onQueryChanged should set correct states and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expected = listOf(
                StationSearchViewState(isLoading = true),
                StationSearchViewState()
            )
            assertThat(states).containsAtLeastElementsIn(expected)

            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case returns GenericError onQueryChanged should log query and result`() {
        collector.test { _, _ ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flowOf(StationListResult.GenericError)

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val message = "Query: $SEARCH_QUERY. Result: ${StationListResult.GenericError}"
            verify { mockLogger.debug(message) }
        }
    }

    @Test
    fun `when use case throws IOException onQueryChanged should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flow { throw IOException() }

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.NETWORK)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case throws generic exception onQueryChanged should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flow { throw Throwable() }

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            val expectedAction = StationSearchViewAction.ShowErrorDialogue(UiSearchError.GENERIC)
            assertThat(actions).contains(expectedAction)
        }
    }

    @Test
    fun `when use case throws exception onQueryChanged should log exception`() {
        collector.test { _, _ ->
            // GIVEN
            val exception = Throwable()
            every {
                mockSearchStationUseCase(SEARCH_QUERY)
            } returns flow { throw exception }

            // WHEN
            viewModel.onQueryChanged(SEARCH_QUERY)

            // THEN
            verify { mockLogger.error(exception) }
        }
    }
}
