package com.alancamargo.tubecalculator.search.ui.adapter

import android.content.Context
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.R
import com.alancamargo.tubecalculator.search.databinding.ItemStationBinding

internal class StationViewHolder(
    private val binding: ItemStationBinding,
    private val onItemClick: (UiStation) -> Unit
) : ViewHolder(binding.root) {

    fun bindTo(station: UiStation) = with(binding) {
        imageContainer.addIconsForModes(station.modes)
        txtName.text = station.name
        root.setOnClickListener { onItemClick(station) }
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
            marginStart = context.resources.getDimensionPixelSize(R.dimen.spacing_8)
        }

        return ImageView(context).apply {
            layoutParams = params
            setImageResource(mode.iconRes)
            contentDescription = context.getString(mode.contentDescriptionRes)
        }
    }
}
