package com.alancamargo.tubecalculator.search.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal class StationDiffUtil : DiffUtil.ItemCallback<UiStation>() {

    override fun areItemsTheSame(oldItem: UiStation, newItem: UiStation): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UiStation, newItem: UiStation): Boolean {
        return oldItem == newItem
    }
}
