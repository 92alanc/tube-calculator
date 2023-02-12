package com.alancamargo.tubecalculator.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.common.ui.model.JourneyType
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.home.R
import com.alancamargo.tubecalculator.home.databinding.ActivityHomeBinding
import com.alancamargo.tubecalculator.home.ui.adapter.JourneyAdapter
import com.alancamargo.tubecalculator.home.ui.viewmodel.HomeViewAction
import com.alancamargo.tubecalculator.home.ui.viewmodel.HomeViewModel
import com.alancamargo.tubecalculator.home.ui.viewmodel.HomeViewState
import com.alancamargo.tubecalculator.navigation.FaresActivityNavigation
import com.alancamargo.tubecalculator.navigation.SearchActivityNavigation
import com.alancamargo.tubecalculator.navigation.SettingsActivityNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.alancamargo.tubecalculator.core.design.R as R2

@AndroidEntryPoint
internal class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding: ActivityHomeBinding
        get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { JourneyAdapter(viewModel::onJourneyClicked) }

    private val actionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBar.toolbar,
            R2.string.nav_open,
            R2.string.nav_close
        )
    }

    @Inject
    lateinit var adLoader: AdLoader

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    @Inject
    lateinit var settingsActivityNavigation: SettingsActivityNavigation

    @Inject
    lateinit var faresActivityNavigation: FaresActivityNavigation

    @Inject
    lateinit var searchActivityNavigation: SearchActivityNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.drawerLayout)
        setUpUi()
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
        viewModel.onCreate(isFirstLaunch = savedInstanceState == null)
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
        appBar.content.btAdd.setOnClickListener { viewModel.onAddClicked() }
        appBar.content.btAddRailJourney.setOnClickListener { viewModel.onAddRailJourneyClicked() }
        appBar.content.btAddBusAndTramJourney.setOnClickListener {
            viewModel.onAddBusAndTramJourneyClicked()
        }
        appBar.content.recyclerView.adapter = adapter
        adLoader.loadBannerAds(appBar.content.banner)
    }

    private fun handleState(state: HomeViewState) = with(binding.appBar.content) {
        state.journeys?.let(adapter::submitList)
        btAdd.isVisible = state.showAddButton
        btCalculate.isVisible = state.showCalculateButton
        groupFabs.isVisible = state.isAddButtonExpanded
        btAdd.isExtended = state.isAddButtonExpanded
        txtEmptyState.isVisible = state.journeys.isNullOrEmpty()

        if (state.showAddRailJourneyButton) {
            btAddRailJourney.show()
            labelRailJourney.isVisible = true
        } else {
            btAddRailJourney.hide()
            labelRailJourney.isVisible = false
        }

        if (state.showAddBusAndTramJourneyButton) {
            btAddBusAndTramJourney.show()
            labelBusAndTramJourney.isVisible = true
        } else {
            btAddBusAndTramJourney.hide()
            labelBusAndTramJourney.isVisible = false
        }
    }

    private fun handleAction(action: HomeViewAction) {
        when (action) {
            is HomeViewAction.ShowAppInfo -> showAppInfoDialogue(action.appVersionName)
            is HomeViewAction.NavigateToSettings -> navigateToSettings()
            is HomeViewAction.ShowPrivacyPolicyDialogue -> showPrivacyPolicyDialogue()
            is HomeViewAction.ShowFirstAccessDialogue -> showFirstAccessDialogue()
            is HomeViewAction.EditJourney -> editJourney(action.journey)
            is HomeViewAction.NavigateToFares -> navigateToFares(action.journeys)
            is HomeViewAction.AddRailJourney -> addRailJourney()
            is HomeViewAction.AddBusAndTramJourney -> addBusAndTramJourney()
        }
    }

    private fun ActivityHomeBinding.setUpNavigationDrawer() {
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(::onNavigationItemSelected)
    }

    private fun setUpCalculateButton() {
        binding.appBar.content.btCalculate.setOnClickListener {
            viewModel.onCalculateClicked()
        }
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

    private fun navigateToFares(journeys: List<Journey>) {
        val railJourney = journeys.find { it is Journey.Rail } as? Journey.Rail
        val busAndTramJourney = journeys.find { it is Journey.BusAndTram } as? Journey.BusAndTram

        faresActivityNavigation.startActivity(
            context = this,
            origin = railJourney?.origin,
            destination = railJourney?.destination,
            busAndTramJourneyCount = busAndTramJourney?.journeyCount ?: 0
        )
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
            messageRes = R.string.home_app_info
        )
    }

    private fun editJourney(journey: Journey) {
        searchActivityNavigation.startActivityForResult(
            activity = this,
            journey = journey
        ) { result ->

        }
    }

    private fun addRailJourney() {
        val journeyType = JourneyType.RAIL
        searchActivityNavigation.startActivityForResult(
            activity = this,
            journeyType = journeyType
        ) { result ->

        }
    }

    private fun addBusAndTramJourney() {
        val journeyType = JourneyType.BUS_AND_TRAM
        searchActivityNavigation.startActivityForResult(
            activity = this,
            journeyType = journeyType
        ) { result ->

        }
    }

    companion object {
        fun getIntent(context: Context): Intent = context.createIntent(HomeActivity::class)
    }
}
