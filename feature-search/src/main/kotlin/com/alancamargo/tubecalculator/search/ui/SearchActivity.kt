package com.alancamargo.tubecalculator.search.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
import com.alancamargo.tubecalculator.search.ui.fragments.BusAndTramJourneysFragment
import com.alancamargo.tubecalculator.search.ui.fragments.StationSearchFragment
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewModel
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewState
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

    private var originFragment: StationSearchFragment? = null
    private var destinationFragment: StationSearchFragment? = null
    private var busAndTramJourneysFragment: BusAndTramJourneysFragment? = null

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    @Inject
    lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
        viewModel.onCreate(
            isFirstLaunch = savedInstanceState == null,
            args.journey,
            args.journeyType
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        viewModel.onBackPressed()
        return true
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btNext.setOnClickListener { viewModel.onNextClicked() }
        adLoader.loadBannerAds(banner)
    }

    private fun handleState(state: SearchViewState) {
        binding.btNext.isVisible = state.showNextButton
    }

    private fun handleAction(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)

            is SearchViewAction.AttachBlankRailJourneyFragments -> {
                attachRailJourneyFragments(journey = null)
            }

            is SearchViewAction.AttachBlankBusAndTramJourneyFragment -> {
                attachBusAndTramJourneyFragment(journey = null)
            }

            is SearchViewAction.AttachPreFilledRailJourneyFragments -> {
                attachRailJourneyFragments(action.journey)
            }

            is SearchViewAction.AttachPreFilledBusAndTramJourneyFragment -> {
                attachBusAndTramJourneyFragment(action.journey)
            }

            is SearchViewAction.Finish -> finish()

            is SearchViewAction.SendJourney -> sendJourney(action.journey)
        }
    }

    private fun showErrorDialogue(error: UiSearchError) {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R2.string.error,
            messageRes = error.messageRes
        )
    }

    private fun attachRailJourneyFragments(journey: Journey.Rail?) {
        val originFragment = StationSearchFragment.newInstance(
            searchType = SearchType.ORIGIN,
            station = journey?.origin,
            onStationSelected = viewModel::onOriginSelected
        )

        val destinationFragment = StationSearchFragment.newInstance(
            searchType = SearchType.DESTINATION,
            station = journey?.destination,
            onStationSelected = viewModel::onDestinationSelected
        )

        supportFragmentManager.commit {
            replace(binding.topContainer.id, originFragment, TAG_ORIGIN)
            replace(binding.bottomContainer.id, destinationFragment, TAG_DESTINATION)
        }

        this.originFragment = originFragment
        this.destinationFragment = destinationFragment
    }

    private fun attachBusAndTramJourneyFragment(journey: Journey.BusAndTram?) {
        val busAndTramJourneysFragment = BusAndTramJourneysFragment.newInstance(
            journeyCount = journey?.journeyCount ?: 0,
            onJourneyCountSelected = viewModel::onBusAndTramJourneyCountSelected
        )

        supportFragmentManager.commit {
            replace(binding.topContainer.id, busAndTramJourneysFragment, TAG_BUS_AND_TRAM_JOURNEYS)
        }

        this.busAndTramJourneysFragment = busAndTramJourneysFragment
    }

    private fun sendJourney(journey: Journey) {
        val data = Intent().putArguments(journey)
        setResult(Activity.RESULT_OK, data)
        finish()
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
