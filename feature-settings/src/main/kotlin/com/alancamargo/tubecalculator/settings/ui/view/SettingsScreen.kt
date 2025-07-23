package com.alancamargo.tubecalculator.settings.ui.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.model.SwitchData
import com.alancamargo.tubecalculator.core.design.view.ComposableAdView
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.core.design.view.CustomSwitch
import com.alancamargo.tubecalculator.settings.R
import com.alancamargo.tubecalculator.core.design.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    adUnitId: String,
    adLoader: AdLoader,
    analyticsSwitchData: SwitchData,
    crashLoggingSwitchData: SwitchData,
    adPersonalisationSwitchData: SwitchData,
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { CustomFontText(text = stringResource(CoreR.string.settings)) },
                colors = TopAppBarColors(
                    containerColor = colorResource(CoreR.color.white),
                    scrolledContainerColor = colorResource(CoreR.color.white),
                    navigationIconContentColor = colorResource(CoreR.color.black),
                    titleContentColor = colorResource(CoreR.color.black),
                    actionIconContentColor = colorResource(CoreR.color.black)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(CoreR.string.content_description_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(all = dimensionResource(CoreR.dimen.spacing_16)),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(CoreR.dimen.spacing_8)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomFontText(text = stringResource(R.string.title_analytics))
                    CustomSwitch(analyticsSwitchData)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomFontText(text = stringResource(R.string.title_crash_logging))
                    CustomSwitch(crashLoggingSwitchData)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomFontText(text = stringResource(R.string.title_personalised_ads))
                    CustomSwitch(adPersonalisationSwitchData)
                }
            }

            Spacer(
                modifier = Modifier.height(dimensionResource(CoreR.dimen.spacing_8))
            )

            ComposableAdView(
                modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally),
                adUnitId = adUnitId,
                adLoader = adLoader
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        adUnitId = "",
        adLoader = object : AdLoader {
            override fun loadBannerAds(target: View) {}

            override fun loadInterstitialAds(
                activity: AppCompatActivity,
                adIdRes: Int
            ) {}
        },
        analyticsSwitchData = SwitchData(
            isEnabled = true,
            onChanged = {}
        ),
        crashLoggingSwitchData = SwitchData(
            isEnabled = true,
            onChanged = {}
        ),
        adPersonalisationSwitchData = SwitchData(
            isEnabled = false,
            onChanged = {}
        ),
        onBackClicked = {}
    )
}
