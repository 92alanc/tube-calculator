package com.alancamargo.tubecalculator.search.ui.model

internal data class BusAndTramJourneySectionData(
    val count: Int,
    val onCountIncreased: () -> Unit,
    val onCountDecreased: () -> Unit,
    val onMoreInformationClicked: () -> Unit
)
