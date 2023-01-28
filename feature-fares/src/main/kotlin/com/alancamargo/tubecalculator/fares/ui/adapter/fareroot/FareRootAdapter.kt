package com.alancamargo.tubecalculator.fares.ui.adapter.fareroot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.core.tools.GenericDiffCallback
import com.alancamargo.tubecalculator.fares.databinding.ItemBusTramFareBinding
import com.alancamargo.tubecalculator.fares.databinding.ItemFareListRootBinding
import com.alancamargo.tubecalculator.fares.domain.model.Fare

private const val VIEW_TYPE_RAIL_FARES = 0
private const val VIEW_TYPE_BUS_AND_TRAM_FARE = 1

internal class FareRootAdapter(
    private val onMessagesButtonClicked: (List<String>) -> Unit
) : ListAdapter<Fare, FareRootViewHolder>(GenericDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FareRootViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_RAIL_FARES -> {
                val binding = ItemFareListRootBinding.inflate(inflater, parent, false)
                RailFaresViewHolder(binding, onMessagesButtonClicked)
            }

            VIEW_TYPE_BUS_AND_TRAM_FARE -> {
                val binding = ItemBusTramFareBinding.inflate(inflater, parent, false)
                BusAndTramFareViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: FareRootViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Fare.RailFare -> VIEW_TYPE_RAIL_FARES
            is Fare.BusAndTramFare -> VIEW_TYPE_BUS_AND_TRAM_FARE
        }
    }
}
