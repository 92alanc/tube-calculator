package com.alancamargo.tubecalculator.home.ui.adapter.viewholder

import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.home.R
import com.alancamargo.tubecalculator.home.databinding.ItemBusTramJourneyBinding

internal class BusAndTramJourneyViewHolder(
    private val binding: ItemBusTramJourneyBinding,
    onItemClick: (Journey) -> Unit
) : JourneyViewHolder(binding.root, onItemClick) {

    override fun bindTo(journey: Journey) = with(binding) {
        require(journey is Journey.BusAndTram)

        txtBusAndTramJourneyCount.text = root.context.getString(
            R.string.number_of_journeys_format,
            journey.journeyCount
        )

        root.setOnClickListener { onItemClick(journey) }
    }
}
