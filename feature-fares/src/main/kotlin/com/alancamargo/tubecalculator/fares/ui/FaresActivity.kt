package com.alancamargo.tubecalculator.fares.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.fares.databinding.ActivityFaresBinding
import com.alancamargo.tubecalculator.fares.ui.viewmodel.FaresViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
internal class FaresActivity : AppCompatActivity() {

    private var _binding: ActivityFaresBinding? = null
    private val binding: ActivityFaresBinding
        get() = _binding!!

    private val viewModel by viewModels<FaresViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFaresBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @Parcelize
    data class Args(
        val origin: UiStation,
        val destination: UiStation,
        val busAndTramJourneyCount: Int
    ) : Parcelable

    companion object {
        fun getIntent(
            context: Context,
            origin: UiStation,
            destination: UiStation,
            busAndTramJourneyCount: Int
        ): Intent {
            val args = Args(origin, destination, busAndTramJourneyCount)
            return context.createIntent(FaresActivity::class).putArguments(args)
        }
    }
}
