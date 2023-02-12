package com.alancamargo.tubecalculator.home.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alancamargo.tubecalculator.common.ui.model.Journey

internal object JourneyDiffCallback : DiffUtil.ItemCallback<Journey>() {

    override fun areItemsTheSame(oldItem: Journey, newItem: Journey): Boolean {
        val rail = oldItem is Journey.Rail && newItem is Journey.Rail
        val busAndTram = oldItem is Journey.BusAndTram && newItem is Journey.BusAndTram

        return if (rail) {
            require(oldItem is Journey.Rail && newItem is Journey.Rail)
            val sameOrigin = oldItem.origin == newItem.origin
            val sameDestination = oldItem.destination == newItem.destination

            sameOrigin && sameDestination
        } else if (busAndTram) {
            require(oldItem is Journey.BusAndTram && newItem is Journey.BusAndTram)
            oldItem.journeyCount == newItem.journeyCount
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: Journey, newItem: Journey): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}
