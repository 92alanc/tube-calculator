package com.alancamargo.tubecalculator.settings.ui.viewmodel

import com.alancamargo.tubecalculator.core.test.viewmodel.ViewModelFlowCollector
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
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

    private val dispatcher = TestCoroutineDispatcher()

    private val viewModel = SettingsViewModel(
        mockIsCrashLoggingEnabledUseCase,
        mockSetCrashLoggingEnabledUseCase,
        mockIsAdPersonalisationEnabledUseCase,
        mockSetAdPersonalisationEnabledUseCase,
        mockIsAnalyticsEnabledUseCase,
        mockSetAnalyticsEnabledUseCase,
        dispatcher
    )

    private val collector = ViewModelFlowCollector(
        viewModel.state,
        viewModel.action,
        dispatcher
    )

    @Test
    fun `onCreate should set correct state`() {
        collector.test { states, _ ->
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
            assertThat(states).contains(expected)
        }
    }

    @Test
    fun `onCrashLoggingToggled should update state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onCrashLoggingToggled(isEnabled = true)

            // THEN
            val expected = SettingsViewState(isCrashLoggingEnabled = true)
            assertThat(states).contains(expected)
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
    fun `onAdPersonalisationToggled should update state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onAdPersonalisationToggled(isEnabled = true)

            // THEN
            val expected = SettingsViewState(isAdPersonalisationEnabled = true)
            assertThat(states).contains(expected)
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
    fun `onAnalyticsToggled should update state`() {
        collector.test { states, _ ->
            // WHEN
            viewModel.onAnalyticsToggled(isEnabled = true)

            // THEN
            val expected = SettingsViewState(isAnalyticsEnabled = true)
            assertThat(states).contains(expected)
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
    fun `onBackClicked should send Finish action`() {
        collector.test { _, actions ->
            // WHEN
            viewModel.onBackClicked()

            // THEN
            assertThat(actions).contains(SettingsViewAction.Finish)
        }
    }
}
