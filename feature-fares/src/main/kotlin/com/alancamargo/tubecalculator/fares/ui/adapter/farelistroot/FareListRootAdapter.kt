package com.alancamargo.tubecalculator.fares.ui.adapter.farelistroot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.core.tools.GenericDiffCallback
import com.alancamargo.tubecalculator.fares.databinding.ItemFareListRootBinding
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot

internal class FareListRootAdapter(
    private val onMessagesButtonClicked: (List<String>) -> Unit
) : ListAdapter<FareListRoot, FareListRootViewHolder>(GenericDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FareListRootViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFareListRootBinding.inflate(inflater, parent, false)
        return FareListRootViewHolder(binding, onMessagesButtonClicked)
    }

    override fun onBindViewHolder(holder: FareListRootViewHolder, position: Int) {
        val fareListRoot = getItem(position)
        holder.bindTo(fareListRoot)
    }
}
