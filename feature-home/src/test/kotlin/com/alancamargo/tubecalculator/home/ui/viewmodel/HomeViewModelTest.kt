package com.alancamargo.tubecalculator.home.ui.viewmodel

import app.cash.turbine.test
import com.alancamargo.tubecalculator.home.data.analytics.HomeAnalytics
import com.alancamargo.tubecalculator.home.domain.usecase.DisableDeleteJourneyTutorialUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.DisableFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.IsFirstAccessUseCase
import com.alancamargo.tubecalculator.home.domain.usecase.ShouldShowDeleteJourneyTutorialUseCase
import com.alancamargo.tubecalculator.home.testtools.stubBusAndTramJourney
import com.alancamargo.tubecalculator.home.testtools.stubRailJourney
import com.google.common.truth.Truth.assertThat
import io.mockk.every
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
class HomeViewModelTest {

    private val mockIsFirstAccessUseCase = mockk<IsFirstAccessUseCase>(relaxed = true)
    private val mockDisableFirstAccessUseCase = mockk<DisableFirstAccessUseCase>(relaxed = true)
    private val mockShouldShowDeleteJourneyTutorialUseCase = mockk<ShouldShowDeleteJourneyTutorialUseCase>(
        relaxed = true
    )
    private val mockDisableDeleteJourneyTutorialUseCase = mockk<DisableDeleteJourneyTutorialUseCase>(
        relaxed = true
    )
    private val mockAnalytics = mockk<HomeAnalytics>(relaxed = true)
    private val appVersionName = "2023.1.0"
    private val uiDelay = 0L
    private val dispatcher = StandardTestDispatcher()

    private val viewModel = HomeViewModel(
        isFirstAccessUseCase = mockIsFirstAccessUseCase,
        disableFirstAccessUseCase = mockDisableFirstAccessUseCase,
        shouldShowDeleteJourneyTutorialUseCase = mockShouldShowDeleteJourneyTutorialUseCase,
        disableDeleteJourneyTutorialUseCase = mockDisableDeleteJourneyTutorialUseCase,
        analytics = mockAnalytics,
        appVersionName = appVersionName,
        uiDelay = uiDelay,
        dispatcher = dispatcher
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `on first launch onCreate should track screen view event`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = true)

        // THEN
        verify { mockAnalytics.trackScreenViewed() }
    }

    @Test
    fun `on first launch and on first access onCreate should send ShowFirstAccessDialogue action`() {
        runTest {
            // GIVEN
            every { mockIsFirstAccessUseCase() } returns true

            // WHEN
            viewModel.onCreate(isFirstLaunch = true)

            // THEN
            viewModel.action.test {
                assertThat(awaitItem()).isEqualTo(HomeViewAction.ShowFirstAccessDialogue)
            }
        }
    }

    @Test
    fun `on first launch and on first access onCreate should show add journey tutorial`() {
        runTest {
            // GIVEN
            every { mockIsFirstAccessUseCase() } returns true

            // WHEN
            viewModel.onCreate(isFirstLaunch = true)

            // THEN
            val expected = HomeViewState(showAddJourneyTutorial = true)
            viewModel.state.test {
                skipItems(count = 1)
                assertThat(awaitItem()).isEqualTo(expected)
            }
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
    fun `when not on first launch onCreate should not check for first access`() {
        // WHEN
        viewModel.onCreate(isFirstLaunch = false)

        // THEN
        verify(exactly = 0) { mockIsFirstAccessUseCase() }
    }

    @Test
    fun `onFirstAccessGoToSettingsClicked should disable first access`() = runTest {
        // WHEN
        viewModel.onFirstAccessGoToSettingsClicked()

        // THEN
        verify { mockDisableFirstAccessUseCase() }
    }

    @Test
    fun `onFirstAccessGoToSettingsClicked should send NavigateToSettings action`() = runTest {
        // WHEN
        viewModel.onFirstAccessGoToSettingsClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(HomeViewAction.NavigateToSettings)
        }
    }

    @Test
    fun `onFirstAccessNotNowClicked should disable first access`() {
        // WHEN
        viewModel.onFirstAccessNotNowClicked()

        // THEN
        verify { mockDisableFirstAccessUseCase() }
    }

    @Test
    fun `onSettingsClicked should track button click event`() {
        // WHEN
        viewModel.onSettingsClicked()

        // THEN
        verify { mockAnalytics.trackSettingsClicked() }
    }

    @Test
    fun `onSettingsClicked should send NavigateToSettings action`() = runTest {
        // WHEN
        viewModel.onSettingsClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(HomeViewAction.NavigateToSettings)
        }
    }

    @Test
    fun `onPrivacyPolicyClicked should send ShowPrivacyPolicyDialogue action`() = runTest {
        // WHEN
        viewModel.onPrivacyPolicyClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(HomeViewAction.ShowPrivacyPolicyDialogue)
        }
    }

    @Test
    fun `onAppInfoClicked should track button click event`() {
        // WHEN
        viewModel.onAppInfoClicked()

        // THEN
        verify { mockAnalytics.trackAppInfoClicked() }
    }

    @Test
    fun `onAppInfoClicked should send ShowAppInfo action`() = runTest {
        // WHEN
        viewModel.onAppInfoClicked()

        // THEN
        viewModel.action.test {
            val expected = HomeViewAction.ShowAppInfo(appVersionName)
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onJourneyReceived should set correct state`() = runTest {
        // GIVEN
        every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
        val journey = stubBusAndTramJourney()

        // WHEN
        viewModel.onJourneyReceived(journey)

        // THEN
        val expected = HomeViewState(
            journeys = listOf(journey),
            showAddButton = true,
            showCalculateButton = true
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with rail and bus and tram journeys added onJourneyReceived should set correct state`() {
        runTest {
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
            val rail = stubRailJourney()
            val busAndTram = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(rail)
            viewModel.onJourneyReceived(busAndTram)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(rail, busAndTram),
                showAddButton = false,
                showCalculateButton = true,
                showAddJourneyTutorial = false
            )
            viewModel.state.test {
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `with existing rail journey onJourneyReceived should set correct state`() = runTest {
        // GIVEN
        every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
        val rail = stubRailJourney()

        // WHEN
        viewModel.onJourneyReceived(rail)
        viewModel.onJourneyReceived(rail)

        // THEN
        val expected = HomeViewState(
            journeys = listOf(rail),
            showAddButton = true,
            showCalculateButton = true,
            showAddJourneyTutorial = false
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with existing bus and tram journey onJourneyReceived should set correct state`() {
        runTest {
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns false
            val busAndTram = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(busAndTram)
            viewModel.onJourneyReceived(busAndTram)

            // THEN
            val expected = HomeViewState(
                journeys = listOf(busAndTram),
                showAddButton = true,
                showCalculateButton = true,
                showAddJourneyTutorial = false
            )
            viewModel.state.test {
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case returns true onJourneyReceived should send ShowDeleteJourneyTutorial action`() {
        runTest {
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns true
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(journey)

            // THEN
            val illustrationAssetName = "delete_journey.gif"
            val expected = HomeViewAction.ShowDeleteJourneyTutorial(illustrationAssetName)
            viewModel.action.test {
                assertThat(awaitItem()).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `when use case returns true onJourneyReceived should disable delete journey tutorial`() {
        runTest {
            // GIVEN
            every { mockShouldShowDeleteJourneyTutorialUseCase() } returns true
            val journey = stubBusAndTramJourney()

            // WHEN
            viewModel.onJourneyReceived(journey)

            // THEN
            verify { mockDisableDeleteJourneyTutorialUseCase() }
        }
    }

    @Test
    fun `onJourneyRemoved should track event`() {
        // GIVEN
        val journey = stubRailJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onJourneyRemoved(journeyPosition = 0)

        // THEN
        verify { mockAnalytics.trackJourneyRemoved() }
    }

    @Test
    fun `onJourneyRemoved should set correct state`() = runTest {
        // GIVEN
        val journey = stubRailJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onJourneyRemoved(journeyPosition = 0)

        // THEN
        val expected = HomeViewState(
            journeys = emptyList(),
            showAddButton = true,
            showCalculateButton = false
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onCalculateClicked should track button click event`() {
        // GIVEN
        val journey = stubRailJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onCalculateClicked()

        // THEN
        verify { mockAnalytics.trackCalculateClicked(journeys = listOf(journey)) }
    }

    @Test
    fun `onCalculateClicked should send NavigateToSearch action`() = runTest {
        // GIVEN
        val journey = stubBusAndTramJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onCalculateClicked()

        // THEN
        val journeys = listOf(journey)
        val expected = HomeViewAction.NavigateToFares(journeys)
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with no journeys onAddClicked should set correct state`() = runTest {
        // WHEN
        viewModel.onAddClicked()

        // THEN
        val expected = HomeViewState(
            isAddButtonExpanded = true,
            showAddBusAndTramJourneyButton = true,
            showAddRailJourneyButton = true,
            showAddJourneyTutorial = false
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with rail journey onAddClicked should set correct state`() = runTest {
        // GIVEN
        val journey = stubRailJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onAddClicked()

        // THEN
        val expected = HomeViewState(
            journeys = listOf(journey),
            showCalculateButton = true,
            isAddButtonExpanded = true,
            showAddBusAndTramJourneyButton = true,
            showAddRailJourneyButton = false,
            showAddJourneyTutorial = false
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with bus and tram journey onAddClicked should set correct state`() = runTest {
        // GIVEN
        val journey = stubBusAndTramJourney()
        viewModel.onJourneyReceived(journey)

        // WHEN
        viewModel.onAddClicked()

        // THEN
        val expected = HomeViewState(
            journeys = listOf(journey),
            showCalculateButton = true,
            isAddButtonExpanded = true,
            showAddBusAndTramJourneyButton = false,
            showAddRailJourneyButton = true,
            showAddJourneyTutorial = false
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `when button is expanded onAddedClicked should set correct state`() = runTest {
        // GIVEN
        viewModel.onAddClicked()

        // WHEN
        viewModel.onAddClicked()

        // THEN
        val expected = HomeViewState(isAddButtonExpanded = false)
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onJourneyClicked should track event`() {
        // WHEN
        viewModel.onJourneyClicked(journey = stubRailJourney())

        // THEN
        verify { mockAnalytics.trackJourneyClicked() }
    }

    @Test
    fun `onJourneyClicked should send EditJourney event`() = runTest {
        // GIVEN
        val journey = stubRailJourney()

        // WHEN
        viewModel.onJourneyClicked(journey)

        // THEN
        val expected = HomeViewAction.EditJourney(journey)
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onAddRailJourneyClicked should set correct state`() = runTest {
        // WHEN
        viewModel.onAddRailJourneyClicked()

        // THEN
        val expected = HomeViewState(isAddButtonExpanded = false)
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onAddRailJourneyClicked should send AddRailJourney action`() = runTest {
        // WHEN
        viewModel.onAddRailJourneyClicked()

        // THEN
        val expected = HomeViewAction.AddRailJourney
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onAddBusAndTramJourneyClicked should set correct state`() = runTest {
        // WHEN
        viewModel.onAddBusAndTramJourneyClicked()

        // THEN
        val expected = HomeViewState(isAddButtonExpanded = false)
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onAddBusAndTramJourneyClicked should send AddBusAndTramJourney action`() = runTest {
        // WHEN
        viewModel.onAddBusAndTramJourneyClicked()

        // THEN
        val expected = HomeViewAction.AddBusAndTramJourney
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }
}
