package com.alancamargo.tubecalculator.search.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.search.databinding.ItemStationBinding

internal class StationAdapter(context: Context) : ArrayAdapter<UiStation>(
    context,
    R.layout.item_station
) {

    private var stations = emptyList<UiStation>()

    fun submitList(stations: List<UiStation>) {
        this.stations = stations
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return stations.size
    }

    override fun getItem(position: Int): UiStation {
        return stations[position]
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val binding = ItemStationBinding.inflate(inflater, parent, false).apply {
            val station = stations[position]
            imageContainer.addIconsForModes(station.modes)
            txtName.text = station.name
        }

        return binding.root
    }

    private fun GridLayout.addIconsForModes(modes: List<UiMode>) = modes.forEach { mode ->
        val imageView = makeImageView(context, mode)
        addView(imageView)
    }

    private fun makeImageView(context: Context, mode: UiMode): ImageView {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            marginStart = context.resources.getDimensionPixelSize(com.alancamargo.tubecalculator.core.design.R.dimen.spacing_8)
        }

        return ImageView(context).apply {
            layoutParams = params
            setImageResource(mode.iconRes)
            contentDescription = context.getString(mode.contentDescriptionRes)
        }
    }
}
