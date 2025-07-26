package com.alancamargo.tubecalculator.fares.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.createIntent
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.ui.model.UiFare
import com.alancamargo.tubecalculator.fares.ui.model.UiFaresError
import com.alancamargo.tubecalculator.fares.ui.view.FaresScreen
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

    private val args by args<Args>()
    private val viewModel by viewModels<FaresViewModel>()

    @Inject
    lateinit var homeActivityNavigation: HomeActivityNavigation

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    @Inject
    lateinit var adLoader: AdLoader

    private val isLoadingState = mutableStateOf(false)
    private val faresState = mutableStateOf<List<UiFare>?>(null)
    private val cheapestTotalFareState = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaresScreen(
                adUnitId = getString(R.string.ads_banner_fares),
                adLoader = adLoader,
                isLoading = isLoadingState.value,
                fares = faresState.value,
                cheapestTotalFare = cheapestTotalFareState.value,
                onMessagesClicked = viewModel::onMessagesButtonClicked,
                onBackClicked = viewModel::onBackClicked,
                onNewSearchClicked = viewModel::onNewSearchClicked
            )
        }
        observeViewStateAndAction()

        viewModel.onCreate(
            origin = args.origin,
            destination = args.destination,
            busAndTramJourneyCount = args.busAndTramJourneyCount,
            isFirstLaunch = savedInstanceState == null
        )
    }

    private fun observeViewStateAndAction() {
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    private fun handleState(state: FaresViewState) = with(state) {
        isLoadingState.value = isLoading
        faresState.value = fares
        cheapestTotalFareState.value = cheapestTotalFare
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
