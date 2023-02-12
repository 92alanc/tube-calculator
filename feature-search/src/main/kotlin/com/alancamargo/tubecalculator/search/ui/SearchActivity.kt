package com.alancamargo.tubecalculator.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
import com.alancamargo.tubecalculator.search.ui.fragments.BusAndTramJourneysFragment
import com.alancamargo.tubecalculator.search.ui.fragments.StationSearchFragment
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import com.alancamargo.tubecalculator.core.design.R as R2

private const val TAG_ORIGIN = "origin_frag"
private const val TAG_DESTINATION = "destination_frag"
private const val TAG_BUS_AND_TRAM_JOURNEYS = "bus_and_tram_journeys_frag"

@AndroidEntryPoint
internal class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding? = null
    private val binding: ActivitySearchBinding
        get() = _binding!!

    private val args by args<Args>()
    private val viewModel by viewModels<SearchViewModel>()

    private val originFragment = StationSearchFragment.newInstance(SearchType.ORIGIN)
    private val destinationFragment = StationSearchFragment.newInstance(SearchType.DESTINATION)
    private val busAndTramJourneysFragment = BusAndTramJourneysFragment()

    @Inject
    lateinit var faresActivityNavigation: FaresActivityNavigation

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observeViewModelFlow(viewModel.action, ::handleAction)
        viewModel.onCreate(
            isFirstLaunch = savedInstanceState == null,
            args.journey,
            args.journeyType
        )
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adLoader.loadBannerAds(banner)
    }

    private fun handleAction(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
            is SearchViewAction.AttachBlankRailJourneyFragments -> TODO()
            is SearchViewAction.AttachBlankBusAndTramJourneyFragment -> TODO()
            is SearchViewAction.AttachPreFilledRailJourneyFragments -> TODO()
            is SearchViewAction.AttachPreFilledBusAndTramJourneyFragment -> TODO()
        }
    }

    private fun showErrorDialogue(error: UiSearchError) {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R2.string.error,
            messageRes = error.messageRes
        )
    }

    @Parcelize
    data class Args(
        val journey: Journey?,
        val journeyType: JourneyType
    ) : Parcelable

    companion object {

        fun getIntent(context: Context, journey: Journey): Intent {
            val journeyType = if (journey is Journey.Rail) {
                JourneyType.RAIL
            } else {
                JourneyType.BUS_AND_TRAM
            }

            val args = Args(journey, journeyType)
            return context.createIntent(SearchActivity::class).putArguments(args)
        }

        fun getIntent(context: Context, journeyType: JourneyType): Intent {
            val args = Args(journey = null, journeyType)
            return context.createIntent(SearchActivity::class).putArguments(args)
        }
    }
}
