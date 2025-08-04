package com.alancamargo.tubecalculator.core.design.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.alancamargo.tubecalculator.core.design.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    @StringRes titleRes: Int,
    onBackClicked: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CustomFontText(
                        modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                        text = stringResource(titleRes),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarColors(
                    containerColor = colorResource(R.color.white),
                    scrolledContainerColor = colorResource(R.color.white),
                    navigationIconContentColor = colorResource(R.color.black),
                    titleContentColor = colorResource(R.color.black),
                    actionIconContentColor = colorResource(R.color.black)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.content_description_back
                            )
                        )
                    }
                }
            )
        },
        content = content
    )
}
