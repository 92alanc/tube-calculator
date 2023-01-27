package com.alancamargo.tubecalculator.fares.ui.adapter.fareroot

import com.alancamargo.tubecalculator.fares.R
import com.alancamargo.tubecalculator.fares.databinding.ItemBusTramFareBinding
import com.alancamargo.tubecalculator.fares.domain.model.FareRoot

internal class BusAndTramFareViewHolder(
    private val binding: ItemBusTramFareBinding
) : FareRootViewHolder(binding.root) {

    override fun bindTo(item: FareRoot) = with(item as FareRoot.BusAndTramFare) {
        binding.txtBusAndTramFares.text = binding.root.context.getString(
            R.string.fares_cost_format,
            fare
        )
    }
}
