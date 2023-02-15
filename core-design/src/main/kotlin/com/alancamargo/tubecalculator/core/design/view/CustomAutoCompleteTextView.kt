package com.alancamargo.tubecalculator.core.design.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.MaterialAutoCompleteTextView

private const val DROPDOWN_ITEM_MAX_COUNT = 5
private const val PADDING = 34

class CustomAutoCompleteTextView(
    context: Context,
    attributeSet: AttributeSet?
) : MaterialAutoCompleteTextView(context, attributeSet) {

    init {
        setHorizontallyScrolling(true)
    }

    override fun onFilterComplete(count: Int) {
        val itemCount = if (count > DROPDOWN_ITEM_MAX_COUNT) {
            DROPDOWN_ITEM_MAX_COUNT
        } else {
            count
        }

        val individualItemHeight = (height / 2) + PADDING
        dropDownHeight = itemCount * individualItemHeight
        super.onFilterComplete(count)
    }
}
