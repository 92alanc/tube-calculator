package com.alancamargo.tubecalculator.core.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

internal class AnalyticsImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {


}
