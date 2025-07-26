package com.alancamargo.tubecalculator.fares.ui.viewmodel

import app.cash.turbine.test
import com.alancamargo.tubecalculator.common.ui.mapping.toUi
import com.alancamargo.tubecalculator.core.design.text.BulletListFormatter
import com.alancamargo.tubecalculator.core.log.Logger
import com.alancamargo.tubecalculator.fares.data.analytics.FaresAnalytics
import com.alancamargo.tubecalculator.fares.data.work.RailFaresCacheWorkScheduler
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.domain.model.RailFaresResult
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateBusAndTramFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.CalculateCheapestTotalFareUseCase
import com.alancamargo.tubecalculator.fares.domain.usecase.GetRailFaresUseCase
import com.alancamargo.tubecalculator.fares.testtools.BUS_AND_TRAM_FARE
import com.alancamargo.tubecalculator.fares.testtools.BUS_AND_TRAM_JOURNEY_COUNT
import com.alancamargo.tubecalculator.fares.testtools.CHEAPEST_TOTAL_FARE
import com.alancamargo.tubecalculator.fares.testtools.stubStation
import com.alancamargo.tubecalculator.fares.testtools.stubUiRailFare
import com.alancamargo.tubecalculator.fares.ui.mapping.toDomain
import com.alancamargo.tubecalculator.fares.ui.model.UiFare
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class FaresViewModelTest {

    private val mockGetRailFaresUseCase = mockk<GetRailFaresUseCase>()
    private val mockCalculateBusAndTramFareUseCase = mockk<CalculateBusAndTramFareUseCase>()
    private val mockCalculateCheapestTotalFareUseCase = mockk<CalculateCheapestTotalFareUseCase>()
    private val mockBulletListFormatter = mockk<BulletListFormatter>()
    private val mockRailFaresCacheWorkScheduler = mockk<RailFaresCacheWorkScheduler>(relaxed = true)
    private val mockAnalytics = mockk<FaresAnalytics>(relaxed = true)
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    private val viewModel = FaresViewModel(
        mockGetRailFaresUseCase,
        mockCalculateBusAndTramFareUseCase,
        mockCalculateCheapestTotalFareUseCase,
        mockBulletListFormatter,
        mockRailFaresCacheWorkScheduler,
        mockAnalytics,
        mockLogger,
        dispatcher
    )

    private val station = stubStation()
    private val uiStation = station.toUi()

    @Before
    fun setUp() {
        every {
            mockCalculateBusAndTramFareUseCase(BUS_AND_TRAM_JOURNEY_COUNT)
        } returns Fare.BusAndTramFare(BUS_AND_TRAM_FARE)

        every {
            mockCalculateCheapestTotalFareUseCase(fares = any())
        } returns CHEAPEST_TOTAL_FARE

        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `when not on first launch onCreate should not track screen view event`() {
        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = false
        )

        // THEN
        verify(exactly = 0) { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `when not on first launch onCreate should not schedule fares cache background work`() {
        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = false
        )

        // THEN
        verify(exactly = 0) {
            mockRailFaresCacheWorkScheduler.scheduleRailFaresCacheBackgroundWork()
        }
    }

    @Test
    fun `when not on first launch onCreate should not calculate rail fares`() {
        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = false
        )

        // THEN
        verify(exactly = 0) {
            @Suppress("UnusedFlow")
            mockGetRailFaresUseCase(
                origin = any(),
                destination = any()
            )
        }
    }

    @Test
    fun `when not on first launch onCreate should not calculate bus and tram fares`() {
        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = false
        )

        // THEN
        verify(exactly = 0) { mockCalculateBusAndTramFareUseCase(busAndTramJourneyCount = any()) }
    }

    @Test
    fun `when not on first launch onCreate should not calculate cheapest total fare`() {
        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = false
        )

        // THEN
        verify(exactly = 0) { mockCalculateCheapestTotalFareUseCase(fares = any()) }
    }

    @Test
    fun `onCreate should track screen view event`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = any(), destination = any())
        } returns flowOf(RailFaresResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `onCreate should schedule fares cache background work`() = runTest {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = any(), destination = any())
        } returns flowOf(RailFaresResult.Success(
            listOf(stubUiRailFare().toDomain() as Fare.RailFare))
        )

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // WHEN
        advanceUntilIdle()
        verify { mockRailFaresCacheWorkScheduler.scheduleRailFaresCacheBackgroundWork() }
    }

    @Test
    fun `when use case returns Success onCreate should set correct state`() = runTest {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.Success(
            listOf(stubUiRailFare().toDomain() as Fare.RailFare))
        )

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        val fares = listOf(stubUiRailFare(), UiFare.UiBusAndTramFare(BUS_AND_TRAM_FARE))
        viewModel.state.test {
            skipItems(count = 5)
            val expected = FaresViewState(
                isLoading = false,
                fares = fares,
                cheapestTotalFare = CHEAPEST_TOTAL_FARE
            )
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `when use case returns Success onCreate should not log result`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.Success(
            listOf(stubUiRailFare().toDomain() as Fare.RailFare))
        )

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `with only bus and tram fare onCreate should set correct state`() = runTest {
        // WHEN
        viewModel.onCreate(
            origin = null,
            destination = null,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        val expected = FaresViewState(
            fares = listOf(UiFare.UiBusAndTramFare(BUS_AND_TRAM_FARE)),
            cheapestTotalFare = CHEAPEST_TOTAL_FARE
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with only bus and tram fare onCreate should not log result`() {
        // WHEN
        viewModel.onCreate(
            origin = null,
            destination = null,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when use case returns InvalidQueryError onCreate should send ShowErrorDialogue action`() {
        runTest {
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.InvalidQueryError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
                isFirstLaunch = true
            )

            // THEN
            viewModel.action.test {
                val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.INVALID_QUERY)
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case returns NetworkError onCreate should and send ShowErrorDialogue action`() {
        runTest {
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.NetworkError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
                isFirstLaunch = true
            )

            // THEN
            viewModel.action.test {
                val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.NETWORK)
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case returns NetworkError onCreate should not log result`() {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.NetworkError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        verify(exactly = 0) { mockLogger.debug(message = any()) }
    }

    @Test
    fun `when use case returns GenericError onCreate should send ShowErrorDialogue action`() {
        runTest {
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flowOf(RailFaresResult.GenericError)

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
                isFirstLaunch = true
            )

            // THEN
            viewModel.action.test {
                val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.GENERIC)
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case returns GenericError onCreate should log result`() = runTest {
        // GIVEN
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flowOf(RailFaresResult.GenericError)

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        advanceUntilIdle()
        val message =
            "Origin: ${uiStation.name}. Destination: ${uiStation.name}. Result: ${RailFaresResult.GenericError}"
        verify { mockLogger.debug(message) }
    }

    @Test
    fun `when use case throws IOException onCreate should send ShowErrorDialogue action`() {
        runTest {
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flow { throw IOException() }

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
                isFirstLaunch = true
            )

            // THEN
            val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.NETWORK)
            viewModel.action.test {
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case throws generic exception onCreate should send ShowErrorDialogue action`() {
        runTest {
            // GIVEN
            every {
                mockGetRailFaresUseCase(origin = station, destination = station)
            } returns flow { throw Throwable() }

            // WHEN
            viewModel.onCreate(
                origin = uiStation,
                destination = uiStation,
                busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
                isFirstLaunch = true
            )

            // THEN
            val expected = FaresViewAction.ShowErrorDialogue(UiFaresError.GENERIC)
            viewModel.action.test {
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case throws exception onCreate should log exception`() = runTest {
        // GIVEN
        val exception = Throwable()
        every {
            mockGetRailFaresUseCase(origin = station, destination = station)
        } returns flow { throw exception }

        // WHEN
        viewModel.onCreate(
            origin = uiStation,
            destination = uiStation,
            busAndTramJourneyCount = BUS_AND_TRAM_JOURNEY_COUNT,
            isFirstLaunch = true
        )

        // THEN
        advanceUntilIdle()
        verify { mockLogger.error(exception) }
    }

    @Test
    fun `onDismissErrorDialogue should send NavigateToHome action`() = runTest {
        // WHEN
        viewModel.onDismissErrorDialogue()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(FaresViewAction.NavigateToHome)
        }
    }

    @Test
    fun `onNewSearchClicked should track button click event`() {
        // WHEN
        viewModel.onNewSearchClicked()

        // THEN
        verify { mockAnalytics.trackNewSearchClicked() }
    }

    @Test
    fun `onNewSearchClicked should send NavigateToHome action`() = runTest {
        // WHEN
        viewModel.onNewSearchClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(FaresViewAction.NavigateToHome)
        }
    }

    @Test
    fun `onMessagesButtonClicked should track button click event`() {
        // GIVEN
        every { mockBulletListFormatter.getBulletList(strings = any()) } returns ""

        // WHEN
        viewModel.onMessagesButtonClicked(messages = emptyList())

        // THEN
        verify { mockAnalytics.trackMessagesClicked() }
    }

    @Test
    fun `onMessagesButtonClicked should send ShowMessagesDialogue action`() = runTest {
        // GIVEN
        val messages = listOf("Message 1", "Message 2", "Message 3")
        val expected = "Bullet list"
        every { mockBulletListFormatter.getBulletList(messages) } returns expected

        // WHEN
        viewModel.onMessagesButtonClicked(messages)

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(FaresViewAction.ShowMessagesDialogue(expected))
        }
    }

    @Test
    fun `onBackClicked should track button click event`() {
        // WHEN
        viewModel.onBackClicked()

        // THEN
        verify { mockAnalytics.trackBackClicked() }
    }

    @Test
    fun `onBackClicked should send NavigateToHome action`() = runTest {
        // WHEN
        viewModel.onBackClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(FaresViewAction.NavigateToHome)
        }
    }
}
