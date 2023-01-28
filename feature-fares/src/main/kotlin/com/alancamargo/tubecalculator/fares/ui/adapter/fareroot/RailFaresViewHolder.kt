package com.alancamargo.tubecalculator.fares.ui.adapter.fareroot

import androidx.core.view.isVisible
import com.alancamargo.tubecalculator.fares.databinding.ItemFareListRootBinding
import com.alancamargo.tubecalculator.fares.domain.model.FareRoot
import com.alancamargo.tubecalculator.fares.ui.adapter.fare.FareAdapter

internal class RailFaresViewHolder(
    private val binding: ItemFareListRootBinding,
    private val onMessagesButtonClicked: (List<String>) -> Unit
) : FareRootViewHolder(binding.root) {

    private val adapter = FareAdapter()

    override fun bindTo(item: FareRoot) = with(item as FareRoot.RailFare) {
        binding.txtHeader.text = header
        binding.btMessages.isVisible = messages.isNotEmpty()
        binding.btMessages.setOnClickListener { onMessagesButtonClicked(messages) }
        binding.recyclerView.adapter = adapter
        adapter.submitList(fareOptions)
    }
}
