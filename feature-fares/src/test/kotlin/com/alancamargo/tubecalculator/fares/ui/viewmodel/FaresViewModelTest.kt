package com.alancamargo.tubecalculator.fares.ui.viewmodel

import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.core.design.text.BulletListFormatter
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.core.test.ViewModelFlowCollector
import com.alancamargo.tubecalculator.fares.data.work.FaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.domain.model.FareListResult
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.GetFaresUseCase
import com.alancamargo.tubecalculator.fares.testtools.BUS_AND_TRAM_FARE
import com.alancamargo.tubecalculator.fares.testtools.BUS_AND_TRAM_JOURNEY_COUNT
import com.alancamargo.tubecalculator.fares.testtools.stubFareListRoot
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
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
class FaresViewModelTest {

    private val mockGetFaresUseCase = mockk<GetFaresUseCase>()
    private val mockCalculateBusAndTramFareUseCase = mockk<CalculateBusAndTramFareUseCase>()
    private val mockBulletListFormatter = mockk<BulletListFormatter>()
    private val mockFaresCacheWorkScheduler = mockk<FaresCacheWorkScheduler>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = TestCoroutineDispatcher()
    private val viewModel = FaresViewModel(
        mockGetFaresUseCase,
        mockCalculateBusAndTramFareUseCase,
        mockBulletListFormatter,
        mockFaresCacheWorkScheduler,
        mockLogger,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        viewModel.state,
        viewModel.action,
        dispatcher
    )

    private val station = stubStation()
    private val uiStation = station.toUi()

    @Before
    fun setUp() {
        every {
            mockCalculateBusAndTramFareUseCase(BUS_AND_TRAM_JOURNEY_COUNT)
        } returns BUS_AND_TRAM_FARE
    }

    @Test
    fun `onCreate should schedule fares cache background work`() {
        // GIVEN
        every {
            mockGetFaresUseCase(origin = any(), destination = any())
        } returns flowOf(FareListResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // WHEN
        verify { mockFaresCacheWorkScheduler.scheduleFaresCacheBackgroundWork() }
    }

    @Test
    fun `when use case returns Success onCreate should set correct state`() {
        collector.test { states, _ ->
            // GIVEN
            every {
                mockGetFaresUseCase(origin = station, destination = station)
            } returns flowOf(FareListResult.Success(listOf(stubFareListRoot())))

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val railFares = listOf(stubFareListRoot())
            val expected = FaresViewState(
                isLoading = true,
                railFares = railFares,
                busAndTramFare = BUS_AND_TRAM_FARE
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `when use case returns Success onCreate should not log result`() {
        // GIVEN
        every {
            mockGetFaresUseCase(origin = station, destination = station)
        } returns flowOf(FareListResult.Success(listOf(stubFareListRoot())))

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `with only bus and tram fare onCreate should set correct state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onCreate(
                origin = null,
                destination = null,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewState(
                railFares = null,
                busAndTramFare = BUS_AND_TRAM_FARE,
                showOnlyBusAndTramFare = true
            )
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `with only bus and tram fare onCreate should not log result`() {
        // WHEN
        viewModel.onCreate(
            origin = null,
            destination = null,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when use case returns NetworkError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetFaresUseCase(origin = station, destination = station)
            } returns flowOf(FareListResult.NetworkError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewState(isLoading = true, busAndTramFare = BUS_AND_TRAM_FARE)
            assertThat(states).contains(expected)
            assertThat(actions).contains(FaresViewAction.ShowErrorDialogue(UiFaresError.NETWORK))
        }
    }

    @Test
    fun `when use case returns NetworkError onCreate should not log result`() {
        // GIVEN
        every {
            mockGetFaresUseCase(origin = station, destination = station)
        } returns flowOf(FareListResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when use case returns ServerError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetFaresUseCase(origin = station, destination = station)
            } returns flowOf(FareListResult.ServerError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewState(isLoading = true, busAndTramFare = BUS_AND_TRAM_FARE)
            assertThat(states).contains(expected)
            assertThat(actions).contains(FaresViewAction.ShowErrorDialogue(UiFaresError.SERVER))
        }
    }

    @Test
    fun `when use case returns ServerError onCreate should log result`() {
        // GIVEN
        every {
            mockGetFaresUseCase(origin = station, destination = station)
        } returns flowOf(FareListResult.ServerError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        val message = "Origin: ${uiStation.name}. Destination: ${uiStation.name}. Result: ${FareListResult.ServerError}"
        verify { mockLogger.debug(message) }
    }

    @Test
    fun `when use case returns GenericError onCreate should set correct state and send ShowErrorDialogue action`() {
        collector.test { states, actions ->
            // GIVEN
            every {
                mockGetFaresUseCase(origin = station, destination = station)
            } returns flowOf(FareListResult.GenericError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewState(isLoading = true, busAndTramFare = BUS_AND_TRAM_FARE)
            assertThat(states).contains(expected)
            assertThat(actions).contains(FaresViewAction.ShowErrorDialogue(UiFaresError.GENERIC))
        }
    }

    @Test
    fun `when use case returns GenericError onCreate should log result`() {
        // GIVEN
        every {
            mockGetFaresUseCase(origin = station, destination = station)
        } returns flowOf(FareListResult.GenericError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        val message = "Origin: ${uiStation.name}. Destination: ${uiStation.name}. Result: ${FareListResult.GenericError}"
        verify { mockLogger.debug(message) }
    }

    @Test
    fun `when use case throws IOException onCreate should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockGetFaresUseCase(origin = station, destination = station)
            } returns flow { throw IOException() }

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.NETWORK)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws generic exception onCreate should send ShowErrorDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            every {
                mockGetFaresUseCase(origin = station, destination = station)
            } returns flow { throw Throwable() }

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
            )

            // THEN
            val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.GENERIC)
            assertThat(actions).contains(expected)
        }
    }

    @Test
    fun `when use case throws exception onCreate should log exception`() {
        // GIVEN
        val exception = Throwable()
        every {
            mockGetFaresUseCase(origin = station, destination = station)
        } returns flow { throw exception }

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT
        )

        // THEN
        verify { mockLogger.error(exception) }
    }

    @Test
    fun `onDismissErrorDialogue should send Finish action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onDismissErrorDialogue()

            // THEN
            assertThat(actions).contains(FaresViewAction.Finish)
        }
    }

    @Test
    fun `onNewSearchClicked should send NavigateToSearch action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onNewSearchClicked()

            // THEN
            assertThat(actions).contains(FaresViewAction.NavigateToSearch)
        }
    }

    @Test
    fun `onMessagesButtonClicked should send ShowMessagesDialogue action`() {
        collector.test { _, actions ->
            // GIVEN
            val messages = listOf("Message 1", "Message 2", "Message 3")
            val expected = "Bullet list"
            every { mockBulletListFormatter.getBulletList(messages) } returns expected

            // WHEN
            viewModel.onMessagesButtonClicked(messages)

            // THEN
            assertThat(actions).contains(FaresViewAction.ShowMessagesDialogue(expected))
        }
    }
}
