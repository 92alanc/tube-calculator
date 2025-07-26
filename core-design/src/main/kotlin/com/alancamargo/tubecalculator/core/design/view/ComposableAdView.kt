package com.alancamargo.tubecalculator.core.design.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.alancamargo.tubecalculator.core.design.ads.AdLoader
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun ComposableAdView(modifier: Modifier, adUnitId: String, adLoader: AdLoader) {
    AndroidView(
        modifier = modifier,
        factory = ::AdView,
        update = { adView ->
            adView.setAdSize(AdSize.BANNER)
            adView.adUnitId = adUnitId
            adLoader.loadBannerAds(adView)
        }
    )
}
