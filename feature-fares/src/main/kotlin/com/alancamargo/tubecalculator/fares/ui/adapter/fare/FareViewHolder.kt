package com.alancamargo.tubecalculator.fares.ui.adapter.fare

import androidx.annotation.StringRes
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
        txtOrigin.text = getString(R.string.fares_from_format, fare.origin)
        txtDestination.text = getString(R.string.fares_to_format, fare.destination)
        txtPassengerType.text = getString(R.string.fares_passenger_type_format, fare.passengerType)
        recyclerView.adapter = adapter
        adapter.submitList(fare.tickets)
    }

    private fun ItemFareBinding.getString(@StringRes stringRes: Int, vararg args: String): String {
        return root.context.getString(stringRes, args)
    }
}
