package com.alancamargo.tubecalculator.home.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.home.R
import com.alancamargo.tubecalculator.home.databinding.ItemBusTramJourneyBinding

internal class BusAndTramJourneyViewHolder(
    private val binding: ItemBusTramJourneyBinding,
    private val onItemClick: (Journey) -> Unit
) : ViewHolder(binding.root) {

    fun bindTo(journey: Journey.BusAndTram) = with(binding) {
        txtBusAndTramJourneyCount.text = root.context.getString(
            R.string.number_of_journeys_format,
            journey.journeyCount
        )
        root.setOnClickListener { onItemClick(journey) }
    }
}
