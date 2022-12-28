package com.alancamargo.tubecalculator.fares.ui.adapter.farelistroot

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.fares.databinding.ItemFareListRootBinding
import com.alancamargo.tubecalculator.fares.domain.model.FareListRoot
import com.alancamargo.tubecalculator.fares.ui.adapter.fare.FareAdapter

internal class FareListRootViewHolder(
    private val binding: ItemFareListRootBinding,
    private val onMessagesButtonClicked: (List<String>) -> Unit
) : ViewHolder(binding.root) {

    private val adapter = FareAdapter()

    fun bindTo(fareListRoot: FareListRoot) = with(binding) {
        txtHeader.text = fareListRoot.header
        val messages = fareListRoot.messages
        btMessages.isVisible = messages.isNotEmpty()
        btMessages.setOnClickListener { onMessagesButtonClicked(messages) }
        recyclerView.adapter = adapter
        adapter.submitList(fareListRoot.fares)
    }
}
