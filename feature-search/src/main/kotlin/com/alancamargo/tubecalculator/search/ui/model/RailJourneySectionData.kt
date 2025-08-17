package com.alancamargo.tubecalculator.search.ui.model

import androidx.compose.foundation.text.input.TextFieldState
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class RailJourneySectionData(
    val textFieldState: TextFieldState,
    val searchType: SearchType,
    val searchResults: List<UiStation>?,
    val selectedStation: UiStation?,
    val onQueryChanged: (String) -> Unit,
    val onStationSelected: (UiStation) -> Unit
)
