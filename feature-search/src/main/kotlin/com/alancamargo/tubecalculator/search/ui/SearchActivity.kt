package com.alancamargo.tubecalculator.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.tools.AdLoader
import com.alancamargo.tubecalculator.core.design.tools.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
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
    lateinit var settingsActivityNavigation: SettingsActivityNavigation

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
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemSettings -> {
                viewModel.onSettingsClicked()
                true
            }

            R.id.itemAbout -> {
                viewModel.onAppInfoClicked()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleAction(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.NavigateToFares -> navigateToFares(
                origin = action.origin,
                destination = action.destination,
                busAndTramJourneyCount = action.busAndTramJourneyCount
            )

            is SearchViewAction.ShowAppInfo -> showAppInfoDialogue()

            is SearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)

            is SearchViewAction.NavigateToSettings -> navigateToSettings()

            is SearchViewAction.ShowFirstAccessDialogue -> showFirstAccessDialogue()
        }
    }

    private fun setUpUi() = with(binding) {
        setSupportActionBar(binding.toolbar)
        setUpCalculateButton()
        setUpFragments()
        adLoader.loadBannerAds(binding.banner)
    }

    private fun setUpCalculateButton() {
        binding.btCalculate.setOnClickListener {
            val origin = originFragment.getSelectedStation()
            val destination = destinationFragment.getSelectedStation()
            val busAndTramJourneyCount = busAndTramJourneysFragment.getBusAndTramJourneyCount()

            viewModel.onCalculateClicked(
                origin = origin,
                destination = destination,
                busAndTramJourneyCount = busAndTramJourneyCount
            )
        }
    }

    private fun ActivitySearchBinding.setUpFragments() {
        val origin = supportFragmentManager.findFragmentByTag(TAG_ORIGIN)
        val destination = supportFragmentManager.findFragmentByTag(TAG_DESTINATION)
        val busAndTramJourneys = supportFragmentManager.findFragmentByTag(TAG_BUS_AND_TRAM_JOURNEYS)

        if (origin == null || destination == null || busAndTramJourneys == null) {
            addFragments()
        }
    }

    private fun ActivitySearchBinding.addFragments() {
        supportFragmentManager.beginTransaction()
            .replace(
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
            ).commit()
    }

    private fun navigateToFares(
        origin: UiStation?,
        destination: UiStation?,
        busAndTramJourneyCount: Int
    ) {
        faresActivityNavigation.startActivity(
            context = this,
            origin = origin,
            destination = destination,
            busAndTramJourneyCount = busAndTramJourneyCount
        )
    }

    private fun showFirstAccessDialogue() {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R.string.first_access_title,
            messageRes = R.string.first_access_message,
            buttonTextRes = R.string.sounds_good,
            onDismiss = viewModel::onFirstAccessDialogueDismissed
        )
    }

    private fun navigateToSettings() {
        settingsActivityNavigation.startActivity(context = this)
    }

    private fun showAppInfoDialogue() {
        dialogueHelper.showDialogue(
            context = this,
            iconRes = R2.mipmap.ic_launcher,
            titleRes = R2.string.app_name,
            messageRes = R.string.search_app_info
        )
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
