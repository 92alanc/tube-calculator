package com.alancamargo.tubecalculator.search.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
import com.alancamargo.tubecalculator.search.ui.fragments.BusAndTramJourneysFragment
import com.alancamargo.tubecalculator.search.ui.fragments.StationSearchFragment
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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

            is SearchViewAction.ShowAppInfo -> showAppInfo()

            is SearchViewAction.ShowErrorDialogue -> {
                // TODO
            }
        }
    }

    private fun setUpUi() = with(binding) {
        setUpCalculateButton()
        setUpFragments()
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
        origin: UiStation,
        destination: UiStation,
        busAndTramJourneyCount: Int
    ) {
        faresActivityNavigation.startActivity(
            context = this,
            origin = origin,
            destination = destination,
            busAndTramJourneyCount = busAndTramJourneyCount
        )
    }

    private fun showAppInfo() {
        MaterialAlertDialogBuilder(this).setTitle(R2.string.app_name)
            .setMessage(R.string.search_app_info)
            .setNeutralButton(R2.string.ok, null)
            .setIcon(R2.mipmap.ic_launcher)
            .show()
    }
}
