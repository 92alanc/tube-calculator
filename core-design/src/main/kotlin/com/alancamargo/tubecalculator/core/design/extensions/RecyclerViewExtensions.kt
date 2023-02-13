package com.alancamargo.tubecalculator.core.design.extensions

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alancamargo.tubecalculator.core.design.view.SwipeToDeleteCallback

fun RecyclerView.attachSwipeToDeleteHelper(onSwiped: (Int) -> Unit) {
    val callback = SwipeToDeleteCallback(context, onSwiped)
    ItemTouchHelper(callback).attachToRecyclerView(this)
}
