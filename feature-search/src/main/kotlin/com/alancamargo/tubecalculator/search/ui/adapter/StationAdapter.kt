package com.alancamargo.tubecalculator.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.databinding.ItemStationBinding

internal class StationAdapter(
    private val onItemClick: (UiStation) -> Unit
) : ListAdapter<UiStation, StationViewHolder>(StationDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStationBinding.inflate(inflater, parent, false)
        return StationViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = getItem(position)
        holder.bindTo(station)
    }
}
