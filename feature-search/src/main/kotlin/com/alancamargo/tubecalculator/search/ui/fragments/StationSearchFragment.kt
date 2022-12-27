package com.alancamargo.tubecalculator.search.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alancamargo.tubecalculator.common.ui.model.UiStation
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

@AndroidEntryPoint
internal class StationSearchFragment : Fragment() {

    private var _binding: FragmentStationSearchBinding? = null
    private val binding: FragmentStationSearchBinding
        get() = _binding!!

    private val args by args<Args>()
    private val viewModel by viewModels<StationSearchViewModel>()
    private val adapter by lazy { StationAdapter(viewModel::onStationSelected) }

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
        recyclerView.adapter = adapter
        btSearch.setOnClickListener {
            val query = edtSearch.text?.toString().orEmpty()
            viewModel.searchStation(query)
        }
    }

    private fun handleState(state: StationSearchViewState) = with(state) {
        //binding.btSearch.isEnabled = isSearchButtonEnabled
        binding.shimmer.isVisible = isLoading
        binding.recyclerView.isVisible = searchResults != null
        binding.emptyState.isVisible = showEmptyState
        searchResults?.let(adapter::submitList)
    }

    private fun handleAction(action: StationSearchViewAction) {
        when (action) {
            is StationSearchViewAction.ShowErrorDialogue -> showErrorDialogue(action.error)
        }
    }

    private fun showErrorDialogue(error: UiSearchError) {
        // TODO
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
