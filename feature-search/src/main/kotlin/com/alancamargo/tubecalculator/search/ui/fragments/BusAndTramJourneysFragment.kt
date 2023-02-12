package com.alancamargo.tubecalculator.search.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.databinding.FragmentBusAndTramJourneysBinding
import com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys.BusAndTramJourneysViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys.BusAndTramJourneysViewModel
import com.alancamargo.tubecalculator.search.ui.viewmodel.busandtramjourneys.BusAndTramJourneysViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
internal class BusAndTramJourneysFragment : Fragment() {

    private var _binding: FragmentBusAndTramJourneysBinding? = null
    private val binding: FragmentBusAndTramJourneysBinding
        get() = _binding!!

    private val args by args<Args>()
    private val viewModel by viewModels<BusAndTramJourneysViewModel>()

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusAndTramJourneysBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
        setUpUi()
        viewModel.onCreate(args.journeyCount)
    }

    private fun handleState(state: BusAndTramJourneysViewState) = with(state) {
        binding.txtBusAndTramJourneyCount.text = busAndTramJourneyCount.toString()
        args.onJourneyCountSelected(busAndTramJourneyCount)
    }

    private fun handleAction(action: BusAndTramJourneysViewAction) {
        when (action) {
            is BusAndTramJourneysViewAction.ShowMoreInfo -> showMoreInfo()
        }
    }

    private fun showMoreInfo() {
        dialogueHelper.showDialogue(
            context = requireContext(),
            titleRes = R.string.bus_tram_journeys,
            messageRes = R.string.search_bus_tram_journeys_info
        )
    }

    private fun setUpUi() {
        with(binding) {
            btUp.setOnClickListener { viewModel.increaseBusAndTramJourneyCount() }
            btDown.setOnClickListener { viewModel.decreaseBusAndTramJourneyCount() }
            btInfo.setOnClickListener { viewModel.onMoreInfoClicked() }
        }
    }

    @Parcelize
    data class Args(
        val journeyCount: Int,
        val onJourneyCountSelected: (Int) -> Unit
    ) : Parcelable

    companion object {

        fun newInstance(onJourneyCountSelected: (Int) -> Unit): BusAndTramJourneysFragment {
            val args = Args(journeyCount = 0, onJourneyCountSelected)
            return newInstance(args)
        }

        fun newInstance(
            journeyCount: Int,
            onJourneyCountSelected: (Int) -> Unit
        ): BusAndTramJourneysFragment {
            val args = Args(journeyCount, onJourneyCountSelected)
            return newInstance(args)
        }

        private fun newInstance(args: Args): BusAndTramJourneysFragment {
            return BusAndTramJourneysFragment().putArguments(args)
        }
    }
}
