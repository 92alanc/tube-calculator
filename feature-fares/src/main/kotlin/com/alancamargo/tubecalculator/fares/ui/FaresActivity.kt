package com.alancamargo.tubecalculator.fares.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.databinding.ActivityFaresBinding
import com.alancamargo.tubecalculator.fares.ui.adapter.fareroot.FareRootAdapter
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
import com.alancamargo.tubecalculator.fares.ui.viewmodel.FaresViewAction
import com.alancamargo.tubecalculator.fares.ui.viewmodel.FaresViewModel
import com.alancamargo.tubecalculator.fares.ui.viewmodel.FaresViewState
import com.alancamargo.tubecalculator.navigation.HomeActivityNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import com.alancamargo.tubecalculator.core.design.R as R2

@AndroidEntryPoint
internal class FaresActivity : AppCompatActivity() {

    private var _binding: ActivityFaresBinding? = null
    private val binding: ActivityFaresBinding
        get() = _binding!!

    private val args by args<Args>()
    private val viewModel by viewModels<FaresViewModel>()
    private val adapter by lazy { FareRootAdapter(viewModel::onMessagesButtonClicked) }

    @Inject
    lateinit var homeActivityNavigation: HomeActivityNavigation

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFaresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observeViewStateAndAction()

        viewModel.onCreate(
            origin = args.origin,
            destination = args.destination,
            busAndTramJourneyCount = args.busAndTramJourneyCount,
            isFirstLaunch = savedInstanceState == null
        )
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(toolbar)
        rootRecyclerView.adapter = adapter
        btNewSearch.setOnClickListener { viewModel.onNewSearchClicked() }
        adLoader.loadBannerAds(banner)
        adLoader.loadInterstitialAds(
            activity = this@FaresActivity,
            adIdRes = R.string.ads_interstitial_fares
        )
    }

    private fun observeViewStateAndAction() {
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    private fun handleState(state: FaresViewState) = with(state) {
        binding.shimmerContainer.isVisible = isLoading
        binding.rootRecyclerView.isVisible = fares != null && !isLoading
        binding.txtCheapestTotalFare.isVisible = cheapestTotalFare != null
        cheapestTotalFare?.let {
            binding.txtCheapestTotalFare.text = getString(
                R.string.fares_cheapest_total_fare_format,
                it
            )
        }
        fares?.let(adapter::submitList)
    }

    private fun handleAction(action: FaresViewAction) {
        when (action) {
            is FaresViewAction.NavigateToHome -> navigateToHome()
            is FaresViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
            is FaresViewAction.ShowMessagesDialogue -> showMessagesDialogue(action.text)
        }
    }

    private fun navigateToHome() {
        homeActivityNavigation.startActivity(context = this)
        finish()
    }

    private fun showErrorDialogue(error: UiFaresError) {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R2.string.error,
            messageRes = error.messageRes,
            onDismiss = viewModel::onDismissErrorDialogue
        )
    }

    private fun showMessagesDialogue(text: CharSequence) {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R.string.fares_messages,
            message = text
        )
    }

    @Parcelize
    data class Args(
        val origin: UiStation?,
        val destination: UiStation?,
        val busAndTramJourneyCount: Int
    ) : Parcelable

    companion object {
        fun getIntent(
            context: Context,
            origin: UiStation?,
            destination: UiStation?,
            busAndTramJourneyCount: Int
        ): Intent {
            val args = Args(origin, destination, busAndTramJourneyCount)
            return context.createIntent(FaresActivity::class).putArguments(args)
        }
    }
}
