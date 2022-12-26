package com.alancamargo.tubecalculator.search.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alancamargo.tubecalculator.core.extensions.args
import com.alancamargo.tubecalculator.core.extensions.putArguments
import com.alancamargo.tubecalculator.search.databinding.FragmentStationSearchBinding
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
internal class StationSearchFragment : Fragment() {

    private var _binding: FragmentStationSearchBinding? = null
    private val binding: FragmentStationSearchBinding
        get() = _binding!!

    private val args by args<Args>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStationSearchBinding.inflate(inflater, container, false)
        return binding.root
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
