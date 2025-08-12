package com.alancamargo.tubecalculator.search.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.common.ui.model.UiMode
import com.alancamargo.tubecalculator.common.ui.model.UiStation
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.search.ui.model.SearchType
import com.alancamargo.tubecalculator.core.design.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StationSearchSection(
    textFieldState: TextFieldState,
    searchType: SearchType,
    searchResults: List<UiStation>?,
    selectedStation: UiStation?,
    onQueryChanged: (String) -> Unit,
    onStationSelected: (UiStation) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(CoreR.dimen.spacing_16)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreR.dimen.spacing_8)
        )
    ) {
        CustomFontText(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(searchType.labelRes)
        )

        val isExpanded = !searchResults.isNullOrEmpty() && selectedStation == null
        var isSearchBarExpanded by rememberSaveable { mutableStateOf(isExpanded) }

        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = onQueryChanged,
                    onSearch = {},
                    expanded = isSearchBarExpanded,
                    onExpandedChange = { isExpanded ->
                        isSearchBarExpanded = isExpanded
                    },
                    placeholder = {
                        CustomFontText(text = stringResource(searchType.hintRes))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                )
            },
            expanded = isSearchBarExpanded,
            onExpandedChange = { isExpanded ->
                isSearchBarExpanded = isExpanded
            }
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                searchResults?.forEach { station ->
                    SearchResultItem(station, onStationSelected)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StationSearchSectionPreview() {
    StationSearchSection(
        textFieldState = rememberTextFieldState(),
        searchType = SearchType.ORIGIN,
        searchResults = listOf(
            UiStation(
                id = "12345",
                name = "The busiest station in London",
                modes = listOf(
                    UiMode.UNDERGROUND,
                    UiMode.DLR,
                    UiMode.ELIZABETH_LINE,
                    UiMode.OVERGROUND,
                    UiMode.NATIONAL_RAIL
                )
            ),
            UiStation(
                id = "12345",
                name = "The busiest station in London",
                modes = listOf(
                    UiMode.UNDERGROUND,
                    UiMode.DLR,
                    UiMode.ELIZABETH_LINE,
                    UiMode.OVERGROUND,
                    UiMode.NATIONAL_RAIL
                )
            )
        ),
        selectedStation = null,
        onQueryChanged = {},
        onStationSelected = {}
    )
}
