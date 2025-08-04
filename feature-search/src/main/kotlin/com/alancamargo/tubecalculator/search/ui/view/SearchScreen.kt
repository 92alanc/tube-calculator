package com.alancamargo.tubecalculator.search.ui.view

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.alancamargo.tubecalculator.core.design.model.TextStyle
import com.alancamargo.tubecalculator.core.design.view.ComposableAdView
import com.alancamargo.tubecalculator.core.design.view.CustomFontText
import com.alancamargo.tubecalculator.core.design.view.CustomScaffold
import com.alancamargo.tubecalculator.search.R
import com.alancamargo.tubecalculator.core.design.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    adUnitId: String,
    adLoader: AdLoader,
    onBackClicked: () -> Unit
) {
    CustomScaffold(
        titleRes = R.string.search,
        onBackClicked = onBackClicked
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .background(color = colorResource(CoreR.color.white))
        ) {
            Column(
                modifier = Modifier.padding(
                    top = dimensionResource(CoreR.dimen.spacing_16),
                    start = dimensionResource(CoreR.dimen.spacing_16),
                    end = dimensionResource(CoreR.dimen.spacing_16)
                ).fillMaxWidth()
                    .fillMaxHeight(fraction = 0.9f)
                    .verticalScroll(rememberScrollState())
            ) {
                CustomFontText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.search_where_are_you_travelling_from),
                    textAlign = TextAlign.Center,
                    textStyle = TextStyle.HEADLINE_1
                )
            }

            Spacer(Modifier.height(dimensionResource(CoreR.dimen.spacing_24)))

            ComposableAdView(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                adUnitId = adUnitId,
                adLoader = adLoader
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        adUnitId = "",
        adLoader = object : AdLoader {
            override fun loadBannerAds(target: View) {}
            
            override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {}
        },
        onBackClicked = {}
    )
}
