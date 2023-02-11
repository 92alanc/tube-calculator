package com.alancamargo.tubecalculator.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.common.ui.model.Journey
import com.alancamargo.tubecalculator.core.tools.GenericDiffCallback
import com.alancamargo.tubecalculator.home.databinding.ItemBusTramJourneyBinding
import com.alancamargo.tubecalculator.home.databinding.ItemRailJourneyBinding
import com.alancamargo.tubecalculator.home.ui.adapter.viewholder.BusAndTramJourneyViewHolder
import com.alancamargo.tubecalculator.home.ui.adapter.viewholder.JourneyViewHolder
import com.alancamargo.tubecalculator.home.ui.adapter.viewholder.RailJourneyViewHolder

private const val VIEW_TYPE_RAIL = 0
private const val VIEW_TYPE_BUS_TRAM = 1

internal class JourneyAdapter(
    private val onItemClick: (Journey) -> Unit
) : ListAdapter<Journey, JourneyViewHolder>(GenericDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_RAIL -> {
                val binding = ItemRailJourneyBinding.inflate(inflater, parent, false)
                RailJourneyViewHolder(binding, onItemClick)
            }

            VIEW_TYPE_BUS_TRAM -> {
                val binding = ItemBusTramJourneyBinding.inflate(inflater, parent, false)
                BusAndTramJourneyViewHolder(binding, onItemClick)
            }

            else -> throw IllegalStateException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        val journey = getItem(position)
        holder.bindTo(journey)
    }

    override fun getItemViewType(position: Int): Int {
        val journey = getItem(position)

        return if (journey is Journey.Rail) {
            VIEW_TYPE_RAIL
        } else {
            VIEW_TYPE_BUS_TRAM
        }
    }
}
