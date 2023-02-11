package com.alancamargo.tubecalculator.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import com.alancamargo.tubecalculator.search.R
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

    private val actionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBar.toolbar,
            R2.string.nav_open,
            R2.string.nav_close
        )
    }

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
        setContentView(binding.drawerLayout)
        setUpUi()
        observeViewModelFlow(viewModel.action, ::handleAction)
        viewModel.onCreate(isFirstLaunch = savedInstanceState == null)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setUpUi() = with(binding) {
        setUpNavigationDrawer()
        setSupportActionBar(appBar.toolbar)
        setUpCalculateButton()
        adLoader.loadBannerAds(appBar.content.banner)
    }

    private fun handleAction(action: SearchViewAction) {
        when (action) {
            is SearchViewAction.NavigateToFares -> navigateToFares(
                origin = action.origin,
                destination = action.destination,
                busAndTramJourneyCount = action.busAndTramJourneyCount
            )

            is SearchViewAction.ShowAppInfo -> showAppInfoDialogue(action.appVersionName)

            is SearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)

            is SearchViewAction.NavigateToSettings -> navigateToSettings()

            is SearchViewAction.ShowPrivacyPolicyDialogue -> showPrivacyPolicyDialogue()

            is SearchViewAction.ShowFirstAccessDialogue -> showFirstAccessDialogue()

            is SearchViewAction.AttachFragments -> binding.appBar.content.addFragments()
        }
    }

    private fun ActivitySearchBinding.setUpNavigationDrawer() {
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(::onNavigationItemSelected)
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemSettings -> {
                viewModel.onSettingsClicked()
                binding.drawerLayout.close()
                true
            }

            R.id.itemPrivacy -> {
                viewModel.onPrivacyPolicyClicked()
                true
            }

            R.id.itemAbout -> {
                viewModel.onAppInfoClicked()
                true
            }

            else -> false
        }
    }

    private fun setUpCalculateButton() {
        binding.appBar.content.btCalculate.setOnClickListener {
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

    private fun showPrivacyPolicyDialogue() {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R2.string.privacy_policy,
            messageRes = R2.string.privacy_policy_content
        )
    }

    private fun showFirstAccessDialogue() {
        dialogueHelper.showDialogue(
            context = this,
            titleRes = R.string.first_access_title,
            messageRes = R.string.first_access_message,
            positiveButtonTextRes = R2.string.settings,
            onPositiveButtonClick = viewModel::onFirstAccessGoToSettingsClicked,
            negativeButtonTextRes = R.string.not_now,
            onNegativeButtonClick = viewModel::onFirstAccessNotNowClicked
        )
    }

    private fun navigateToSettings() {
        settingsActivityNavigation.startActivity(context = this)
    }

    private fun showAppInfoDialogue(appVersionName: String) {
        val appName = getString(R2.string.app_name)
        val appNameAndVersion = getString(
            R2.string.app_name_and_version_format,
            appName,
            appVersionName
        )

        dialogueHelper.showDialogue(
            context = this,
            iconRes = R2.mipmap.ic_launcher_round,
            title = appNameAndVersion,
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
