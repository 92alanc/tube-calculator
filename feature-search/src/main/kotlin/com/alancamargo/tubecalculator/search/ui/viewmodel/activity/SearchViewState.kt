package com.alancamargo.tubecalculator.search.ui.viewmodel.activity

internal data class SearchViewState(val showNextButton: Boolean = false) {

    fun showNextButton() = copy(showNextButton = true)

    fun hideNextButton() = copy(showNextButton = false)
}
