package com.alancamargo.tubecalculator.fares.ui.adapter.fare

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.databinding.ItemFareBinding
import com.alancamargo.tubecalculator.fares.domain.model.Fare
import com.alancamargo.tubecalculator.fares.ui.adapter.ticket.TicketAdapter

internal class FareViewHolder(private val binding: ItemFareBinding) : ViewHolder(binding.root) {

    private val adapter = TicketAdapter()

    fun bindTo(fare: Fare) = with(binding) {
        txtLabel.text = fare.label
        txtDescription.text = fare.description
        txtOrigin.text = root.context.getString(R.string.fares_from_format, fare.origin)
        txtDestination.text = root.context.getString(R.string.fares_to_format, fare.destination)
        txtPassengerType.text = root.context.getString(
            R.string.fares_passenger_type_format,
            fare.passengerType
        )
        recyclerView.adapter = adapter
        adapter.submitList(fare.tickets)
    }
}
