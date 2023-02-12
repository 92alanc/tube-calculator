package com.alancamargo.tubecalculator.core.design.ads

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import javax.inject.Inject

@VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
class AdLoaderImpl @Inject internal constructor() : AdLoader {

    override fun loadBannerAds(target: View) {
        (target as? AdView)?.let { adView ->
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

    override fun loadInterstitialAds(activity: AppCompatActivity, adIdRes: Int) {
        val adRequest = AdRequest.Builder().build()
        val adUnitId = activity.getString(adIdRes)
        InterstitialAd.load(activity, adUnitId, adRequest, getLoadCallback(activity))
    }

    private fun getLoadCallback(
        activity: AppCompatActivity
    ) = object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(ad: InterstitialAd) {
            super.onAdLoaded(ad)
            ad.show(activity)
        }
    }
}
