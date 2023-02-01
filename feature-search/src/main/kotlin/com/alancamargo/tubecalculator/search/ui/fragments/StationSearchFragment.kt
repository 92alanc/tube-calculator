package com.alancamargo.tubecalculator.search.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.R
import com.alancamargo.tubecalculator.core.design.dialogue.DialogueHelper
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.hideKeyboard
import com.alancamargo.tubecalculator.core.extensions.observeViewModelFlow
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.search.databinding.FragmentStationSearchBinding
import com.alancamargo.tubecalculator.search.ui.adapter.StationAdapter
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.search.ui.model.UiSearchError
import com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch.StationSearchViewAction
import com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch.StationSearchViewModel
import com.alancamargo.tubecalculator.search.ui.viewmodel.stationsearch.StationSearchViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
internal class StationSearchFragment : Fragment() {

    private var _binding: FragmentStationSearchBinding? = null
    private val binding: FragmentStationSearchBinding
        get() = _binding!!

    private val args by args<Args>()
    private val viewModel by viewModels<StationSearchViewModel>()

    @Inject
    lateinit var dialogueHelper: DialogueHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateAndAction()
        setUpUi()
        viewModel.onCreate()
    }

    fun getSelectedStation(): UiStation? = viewModel.selectedStation

    private fun observeStateAndAction() {
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    private fun setUpUi() = with(binding) {
        txtLabel.setText(args.searchType.labelRes)
        textInputLayout.hint = getString(args.searchType.hintRes)
        autoCompleteTextView.hint = getString(args.searchType.hintRes)
        autoCompleteTextView.addTextChangedListener(getTextWatcher())
        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            (parent.adapter as? StationAdapter)?.let { adapter ->
                val station = adapter.getStation(position)
                viewModel.onStationSelected(station)
                autoCompleteTextView.setText(station.name)
                autoCompleteTextView.hideKeyboard()
            }
        }
    }

    private fun handleState(state: StationSearchViewState) = with(state) {
        minQueryLength?.let {
            binding.autoCompleteTextView.threshold = it
        }

        stations?.let {
            val adapter = StationAdapter(
                context = requireContext(),
                stations = it
            )
            binding.autoCompleteTextView.setAdapter(adapter)
        }
    }

    private fun handleAction(action: StationSearchViewAction) {
        when (action) {
            is StationSearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
        }
    }

    private fun showErrorDialogue(error: UiSearchError) {
        dialogueHelper.showDialogue(
            context = requireContext(),
            titleRes = R.string.error,
            messageRes = error.messageRes
        )
    }

    private fun getTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
            query?.toString()?.let(viewModel::onQueryChanged)
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    @Parcelize
    data class Args(val searchType: SearchType) : Parcelable

    companion object {
        fun newInstance(searchType: SearchType): StationSearchFragment {
            val args = Args(searchType)
            return StationSearchFragment().putArguments(args)
        }
    }
}
