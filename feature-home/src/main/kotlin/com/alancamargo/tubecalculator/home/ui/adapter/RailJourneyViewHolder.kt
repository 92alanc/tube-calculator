package com.alancamargo.tubecalculator.home.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.home.databinding.ItemRailJourneyBinding

internal class RailJourneyViewHolder(
    private val binding: ItemRailJourneyBinding,
    private val onItemClick: (Journey) -> Unit
) : ViewHolder(binding.root) {

    fun bindTo(journey: Journey.Rail) = with(binding) {
        txtOrigin.text = journey.origin.name
        txtDestination.text = journey.destination.name
        root.setOnClickListener { onItemClick(journey) }
    }
}
