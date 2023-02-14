package com.alancamargo.tubecalculator.fares.ui.adapter.ticket

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.databinding.ItemTicketBinding
import com.alancamargo.tubecalculator.fares.domain.model.Ticket
import com.alancamargo.tubecalculator.fares.domain.model.TicketType

internal class TicketViewHolder(private val binding: ItemTicketBinding) : ViewHolder(binding.root) {

    fun bindTo(ticket: Ticket) = with(binding) {
        txtType.setText(getLabelFor(ticket.type))
        txtTimeLabel.text = ticket.time.label
        txtTimeDescription.text = ticket.time.description
        txtCost.text = root.context.getString(R.string.fares_cost_format, ticket.cost)
    }

    private fun getLabelFor(ticketType: TicketType): Int = when (ticketType) {
        TicketType.PAY_AS_YOU_GO -> R.string.fares_pay_as_you_go
        TicketType.CASH -> R.string.fares_cash
    }
}
