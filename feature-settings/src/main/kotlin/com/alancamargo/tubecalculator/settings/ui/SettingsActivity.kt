package com.alancamargo.tubecalculator.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.settings.databinding.ActivitySettingsBinding
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewModel
import com.alancamargo.tubecalculator.settings.ui.viewmodel.SettingsViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SettingsActivity : AppCompatActivity() {

    private var _binding: ActivitySettingsBinding? = null
    private val binding: ActivitySettingsBinding
        get() = _binding!!

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observeViewModelFlow(viewModel.state, ::handleState)
        viewModel.onCreate()
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        switchCrashLogging.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onCrashLoggingToggled(isChecked)
        }
    }

    private fun handleState(state: SettingsViewState) {
        binding.switchCrashLogging.isChecked = state.isCrashLoggingEnabled
    }

    companion object {
        fun getIntent(context: Context): Intent = context.createIntent(SettingsActivity::class)
    }
}
