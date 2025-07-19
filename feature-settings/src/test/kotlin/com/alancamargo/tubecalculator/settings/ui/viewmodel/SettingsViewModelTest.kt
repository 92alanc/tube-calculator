package com.alancamargo.tubecalculator.settings.ui.viewmodel

import app.cash.turbine.test
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.IsAdPersonalisationEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.ads.SetAdPersonalisationEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.analytics.IsAnalyticsEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.analytics.SetAnalyticsEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.IsCrashLoggingEnabledUseCase
import com.alancamargo.tubecalculator.settings.domain.usecase.crash.SetCrashLoggingEnabledUseCase
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
class SettingsViewModelTest {

    private val mockIsCrashLoggingEnabledUseCase = mockk<IsCrashLoggingEnabledUseCase>()
    private val mockSetCrashLoggingEnabledUseCase = mockk<SetCrashLoggingEnabledUseCase>(
        relaxed = true
    )

    private val mockIsAdPersonalisationEnabledUseCase = mockk<IsAdPersonalisationEnabledUseCase>()
    private val mockSetAdPersonalisationEnabledUseCase = mockk<SetAdPersonalisationEnabledUseCase>(
        relaxed = true
    )

    private val mockIsAnalyticsEnabledUseCase = mockk<IsAnalyticsEnabledUseCase>()
    private val mockSetAnalyticsEnabledUseCase = mockk<SetAnalyticsEnabledUseCase>(relaxed = true)

    private val dispatcher = StandardTestDispatcher()

    private val viewModel = SettingsViewModel(
        mockIsCrashLoggingEnabledUseCase,
        mockSetCrashLoggingEnabledUseCase,
        mockIsAdPersonalisationEnabledUseCase,
        mockSetAdPersonalisationEnabledUseCase,
        mockIsAnalyticsEnabledUseCase,
        mockSetAnalyticsEnabledUseCase,
        dispatcher
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `onCreate should set correct state`() = runTest {
        // GIVEN
        every { mockIsCrashLoggingEnabledUseCase() } returns true
        every { mockIsAdPersonalisationEnabledUseCase() } returns true
        every { mockIsAnalyticsEnabledUseCase() } returns true

        // WHEN
        viewModel.onCreate()

        // THEN
        val expected = SettingsViewState(
            isCrashLoggingEnabled = true,
            isAdPersonalisationEnabled = true,
            isAnalyticsEnabled = true
        )
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onCrashLoggingToggled should update state`() = runTest {
        // WHEN
        viewModel.onCrashLoggingToggled(isEnabled = true)

        // THEN
        val expected = SettingsViewState(isCrashLoggingEnabled = true)
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onCrashLoggingToggled should update setting`() {
        // WHEN
        viewModel.onCrashLoggingToggled(isEnabled = true)

        // THEN
        verify { mockSetCrashLoggingEnabledUseCase(isEnabled = true) }
    }

    @Test
    fun `onAdPersonalisationToggled should update state`() = runTest {
        // WHEN
        viewModel.onAdPersonalisationToggled(isEnabled = true)

        // THEN
        val expected = SettingsViewState(isAdPersonalisationEnabled = true)
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onAdPersonalisationToggled should update setting`() {
        // WHEN
        viewModel.onAdPersonalisationToggled(isEnabled = true)

        // THEN
        verify { mockSetAdPersonalisationEnabledUseCase(isEnabled = true) }
    }

    @Test
    fun `onAnalyticsToggled should update state`() = runTest {
        // WHEN
        viewModel.onAnalyticsToggled(isEnabled = true)

        // THEN
        val expected = SettingsViewState(isAnalyticsEnabled = true)
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onAnalyticsToggled should update setting`() {
        // WHEN
        viewModel.onAnalyticsToggled(isEnabled = true)

        // THEN
        verify { mockSetAnalyticsEnabledUseCase(isEnabled = true) }
    }

    @Test
    fun `onBackClicked should send Finish action`() = runTest {
        // WHEN
        viewModel.onBackClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(SettingsViewAction.Finish)
        }
    }
}
