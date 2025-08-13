package com.alancamargo.tubecalculator.search.ui.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.MutableState
import com.alancamargo.tubecalculator.common.ui.model.UiStation

internal data class StationSearchSectionData(
    val textFieldState: TextFieldState,
    val searchType: SearchType,
    val searchResults: List<UiStation>?,
    val selectedStationState: MutableState<UiStation?>,
    val onQueryChanged: (String) -> Unit,
    val onStationSelected: (UiStation) -> Unit
)
