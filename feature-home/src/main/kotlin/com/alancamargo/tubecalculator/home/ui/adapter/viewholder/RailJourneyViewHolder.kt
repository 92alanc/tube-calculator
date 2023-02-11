package com.alancamargo.tubecalculator.home.ui.adapter.viewholder

import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.home.databinding.ItemRailJourneyBinding

internal class RailJourneyViewHolder(
    private val binding: ItemRailJourneyBinding,
    onItemClick: (Journey) -> Unit
) : JourneyViewHolder(binding.root, onItemClick) {

    override fun bindTo(journey: Journey) = with(binding) {
        require(journey is Journey.Rail)

        txtOrigin.text = journey.origin.name
        txtDestination.text = journey.destination.name
        root.setOnClickListener { onItemClick(journey) }
    }
}
