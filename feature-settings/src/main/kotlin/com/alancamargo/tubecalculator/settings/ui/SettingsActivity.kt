package com.alancamargo.tubecalculator.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.settings.databinding.ActivitySettingsBinding
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewAction
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewModel
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SettingsActivity : AppCompatActivity() {

    private var _binding: ActivitySettingsBinding? = null
    private val binding: ActivitySettingsBinding
        get() = _binding!!

    private val viewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observeViewStateAndAction()
        viewModel.onCreate()
    }

    override fun onSupportNavigateUp(): Boolean {
        viewModel.onBackClicked()
        return true
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpAnalytics()
        setUpCrashLogging()
        setUpPersonalisedAds()
        adLoader.loadBannerAds(banner)
    }

    private fun observeViewStateAndAction() {
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    private fun ActivitySettingsBinding.setUpAnalytics() {
        switchAnalytics.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAnalyticsToggled(isChecked)
        }
    }

    private fun ActivitySettingsBinding.setUpCrashLogging() {
        switchCrashLogging.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onCrashLoggingToggled(isChecked)
        }
    }

    private fun ActivitySettingsBinding.setUpPersonalisedAds() {
        switchAdPersonalisation.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAdPersonalisationToggled(isChecked)
        }
    }

    private fun handleState(state: SettingsViewState) = with(binding) {
        switchAnalytics.isChecked = state.isAnalyticsEnabled
        switchCrashLogging.isChecked = state.isCrashLoggingEnabled
        switchAdPersonalisation.isChecked = state.isAdPersonalisationEnabled
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
