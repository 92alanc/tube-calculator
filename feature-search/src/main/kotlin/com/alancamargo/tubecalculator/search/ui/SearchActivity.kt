package com.alancamargo.tubecalculator.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
import com.alancamargo.tubecalculator.search.databinding.ContentSearchBinding
import com.alancamargo.tubecalculator.search.ui.fragments.BusAndTramJourneysFragment
import com.alancamargo.tubecalculator.search.ui.fragments.StationSearchFragment
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        setContentView(binding.drawerLayout)
        setUpUi()
        observeViewModelFlow(viewModel.action, ::handleAction)
        viewModel.onCreate(isFirstLaunch = savedInstanceState == null)
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(appBar.toolbar)
        adLoader.loadBannerAds(appBar.content.banner)
    }

    private fun handleAction(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
            is SearchViewAction.AttachFragments -> binding.appBar.content.addFragments()
        }
    }

    private fun ContentSearchBinding.addFragments() {
        supportFragmentManager.commit {
            replace(
                originContainer.id,
                originFragment,
                TAG_ORIGIN
            ).replace(
                destinationContainer.id,
                destinationFragment,
                TAG_DESTINATION
            ).replace(
                busAndTramJourneysContainer.id,
                busAndTramJourneysFragment,
                TAG_BUS_AND_TRAM_JOURNEYS
            )
        }
    }

    private fun showErrorDialogue(error: UiSearchError) {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R2.string.error,
            messageRes = error.messageRes
        )
    }

    companion object {
        fun getIntent(context: Context): Intent = context.createIntent(SearchActivity::class)
    }
}
