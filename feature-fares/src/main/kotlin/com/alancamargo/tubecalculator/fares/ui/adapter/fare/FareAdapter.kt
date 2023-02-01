package com.alancamargo.tubecalculator.fares.ui.adapter.fare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.core.tools.GenericDiffCallback
import com.alancamargo.tubecalculator.fares.databinding.ItemFareBinding
import com.alancamargo.tubecalculator.fares.domain.model.FareOption

internal class FareAdapter : ListAdapter<FareOption, FareViewHolder>(GenericDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FareViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFareBinding.inflate(inflater, parent, false)
        return FareViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FareViewHolder, position: Int) {
        val fare = getItem(position)
        holder.bindTo(fare)
    }
}
