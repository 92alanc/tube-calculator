package com.alancamargo.tubecalculator.home.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.common.ui.model.Journey

internal abstract class JourneyViewHolder(
    itemView: View,
    protected val onItemClick: (Journey) -> Unit
) : ViewHolder(itemView) {

    abstract fun bindTo(journey: Journey)
}
