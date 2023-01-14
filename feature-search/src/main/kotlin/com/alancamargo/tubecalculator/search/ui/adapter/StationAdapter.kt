package com.alancamargo.tubecalculator.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.tools.GenericDiffCallback
import com.alancamargo.tubecalculator.search.databinding.ItemStationBinding

internal class StationAdapter(
    private val onItemSelected: (UiStation) -> Unit
) : ListAdapter<UiStation, StationViewHolder>(GenericDiffCallback()) {

    private var selectedItem: StationViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStationBinding.inflate(inflater, parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = getItem(position)

        if (itemCount == 1) {
            holder.check()
        }

        holder.bindTo(station) { selectedItem ->
            if (selectedItem != this.selectedItem) {
                this.selectedItem?.uncheck()
            }

            this.selectedItem = selectedItem
            onItemSelected(station)
        }
    }
}
