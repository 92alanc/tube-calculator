package com.alancamargo.tubecalculator.fares.ui.adapter.fareroot

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.fares.domain.model.Fare

internal abstract class FareRootViewHolder(itemView: View) : ViewHolder(itemView) {

    abstract fun bindTo(item: Fare)
}
