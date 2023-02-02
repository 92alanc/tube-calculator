package com.alancamargo.tubecalculator.fares.ui.adapter.fare

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.databinding.ItemFareBinding
import com.alancamargo.tubecalculator.fares.domain.model.FareOption
import com.alancamargo.tubecalculator.fares.ui.adapter.ticket.TicketAdapter

internal class FareViewHolder(private val binding: ItemFareBinding) : ViewHolder(binding.root) {

    private val adapter = TicketAdapter()

    fun bindTo(fareOption: FareOption) = with(binding) {
        txtLabel.text = fareOption.label
        txtDescription.text = fareOption.description
        txtOrigin.text = root.context.getString(R.string.fares_from_format, fareOption.origin)
        txtDestination.text = root.context.getString(R.string.fares_to_format, fareOption.destination)
        txtPassengerType.text = root.context.getString(
            R.string.fares_passenger_type_format,
            fareOption.passengerType
        )
        recyclerView.adapter = adapter
        adapter.submitList(fareOption.tickets)
    }
}
