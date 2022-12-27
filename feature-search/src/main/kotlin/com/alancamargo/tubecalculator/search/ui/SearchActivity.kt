package com.alancamargo.tubecalculator.search.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
import com.alancamargo.tubecalculator.search.ui.fragments.BusAndTramJourneysFragment
import com.alancamargo.tubecalculator.search.ui.fragments.StationSearchFragment
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.activity.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding? = null
    private val binding: ActivitySearchBinding
        get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    private val originFragment = StationSearchFragment.newInstance(SearchType.ORIGIN)
    private val destinationFragment = StationSearchFragment.newInstance(SearchType.DESTINATION)
    private val busAndTramJourneysFragment = BusAndTramJourneysFragment()

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
            is SearchViewAction.NavigateToFares -> {
                // TODO
            }

            is SearchViewAction.ShowAppInfo -> {
                // TODO
            }

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
        supportFragmentManager.beginTransaction()
            .replace(originContainer.id, originFragment)
            .replace(destinationContainer.id, destinationFragment)
            .replace(busAndTramJourneysContainer.id, busAndTramJourneysFragment)
            .commit()
    }
}
