package com.alancamargo.tubecalculator.core.design.tools

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

interface AdLoader {

    fun loadBannerAds(target: View)

    fun loadInterstitialAds(activity: AppCompatActivity, @StringRes adIdRes: Int)
}
