package com.alancamargo.tubecalculator.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.model.SwitchData
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.settings.R
import com.alancamargo.tubecalculator.settings.ui.view.SettingsScreen
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewAction
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewModel
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SettingsActivity : AppCompatActivity() {

    private val viewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var adLoader: AdLoader

    private val isAnalyticsSwitchEnabledState = mutableStateOf(false)
    private val isCrashLoggingSwitchEnabledState = mutableStateOf(false)
    private val isAdPersonalisationSwitchEnabledState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen(
                adUnitId = getString(R.string.ads_banner_settings),
                adLoader = adLoader,
                analyticsSwitchData = SwitchData(
                    isEnabled = isAnalyticsSwitchEnabledState.value,
                    onChanged = viewModel::onAnalyticsToggled
                ),
                crashLoggingSwitchData = SwitchData(
                    isEnabled = isCrashLoggingSwitchEnabledState.value,
                    onChanged = viewModel::onCrashLoggingToggled
                ),
                adPersonalisationSwitchData = SwitchData(
                    isEnabled = isAdPersonalisationSwitchEnabledState.value,
                    onChanged = viewModel::onAdPersonalisationToggled
                ),
                onBackClicked = viewModel::onBackClicked
            )
        }
        observeViewStateAndAction()
        viewModel.onCreate()
    }

    private fun observeViewStateAndAction() {
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    private fun handleState(state: SettingsViewState) {
        isAnalyticsSwitchEnabledState.value = state.isAnalyticsEnabled
        isCrashLoggingSwitchEnabledState.value = state.isCrashLoggingEnabled
        isAdPersonalisationSwitchEnabledState.value = state.isAdPersonalisationEnabled
    }

    private fun handleAction(action: SettingsViewAction) {
        when (action) {
            is SettingsViewAction.Finish -> finish()
        }
    }

    companion object {
        fun getIntent(context: Context): Intent = context.createIntent(SettingsActivity::class)
    }
}
