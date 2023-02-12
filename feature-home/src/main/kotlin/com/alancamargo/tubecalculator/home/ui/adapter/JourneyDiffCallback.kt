package com.alancamargo.tubecalculator.home.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alancamargo.tubecalculator.common.ui.model.Journey

internal object JourneyDiffCallback : DiffUtil.ItemCallback<Journey>() {

    override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean {
        val bothRail = oldItem is Journey.Rail && newItem is Journey.Rail
        val bothBusAndTram = oldItem is Journey.BusAndTram && newItem is Journey.BusAndTram

        return bothRail || bothBusAndTram
    }

    override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean {
        return if (oldItem is Journey.Rail && newItem is Journey.Rail) {
            val sameOrigin = oldItem.origin == newItem.origin
            val sameDestination = oldItem.destination == newItem.destination

            sameOrigin && sameDestination
        } else if (oldItem is Journey.BusAndTram && newItem is Journey.BusAndTram) {
            oldItem.journeyCount == newItem.journeyCount
        } else {
            false
        }
    }
}
