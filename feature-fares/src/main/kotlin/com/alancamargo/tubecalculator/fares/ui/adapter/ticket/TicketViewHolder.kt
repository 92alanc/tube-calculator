package com.alancamargo.tubecalculator.fares.ui.adapter.ticket

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.databinding.ItemTicketBinding
import com.alancamargo.tubecalculator.fares.domain.model.Ticket

internal class TicketViewHolder(private val binding: ItemTicketBinding) : ViewHolder(binding.root) {

    fun bindTo(ticket: Ticket) = with(binding) {
        txtType.text = ticket.type
        txtTimeLabel.text = ticket.time.label
        txtTimeDescription.text = ticket.time.description
        txtCost.text = root.context.getString(R.string.fares_cost_format, ticket.cost)
    }
}
