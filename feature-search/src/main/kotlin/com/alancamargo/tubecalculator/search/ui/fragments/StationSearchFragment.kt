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
    private val adapter by lazy { StationAdapter(context = requireContext()) }

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
    }

    fun getSelectedStation(): UiStation? = viewModel.selectedStation

    private fun observeStateAndAction() {
        observeViewModelFlow(viewModel.state, ::handleState)
        observeViewModelFlow(viewModel.action, ::handleAction)
    }

    private fun setUpUi() = with(binding) {
        txtLabel.setText(args.searchType.labelRes)
        textInputLayout.hint = getString(args.searchType.hintRes)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.addTextChangedListener(getQueryTextWatcher())
        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            (item as? UiStation)?.let { station ->
                viewModel.onStationSelected(station)
                autoCompleteTextView.setText(station.name)
            }
        }
    }

    private fun handleState(state: StationSearchViewState) = with(state) {
        searchResults?.let(adapter::submitList)
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

    private fun getQueryTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            text?.toString()?.let(viewModel::onQueryChanged)
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
