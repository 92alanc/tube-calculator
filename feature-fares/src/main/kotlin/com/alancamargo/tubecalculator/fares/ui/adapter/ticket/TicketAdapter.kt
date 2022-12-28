package com.alancamargo.tubecalculator.fares.ui.adapter.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alancamargo.tubecalculator.core.tools.GenericDiffCallback
import com.alancamargo.tubecalculator.fares.databinding.ItemTicketBinding
import com.alancamargo.tubecalculator.fares.domain.model.Ticket

internal class TicketAdapter : ListAdapter<Ticket, TicketHolder>(GenericDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTicketBinding.inflate(inflater, parent, false)
        return TicketHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {
        val ticket = getItem(position)
        holder.bindTo(ticket)
    }
}
