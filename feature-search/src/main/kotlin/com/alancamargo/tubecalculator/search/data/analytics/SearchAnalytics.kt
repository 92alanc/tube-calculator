package com.alancamargo.tubecalculator.search.data.analytics

internal interface SearchAnalytics {

    fun trackScreenViewed()

    fun trackCalculateClicked(
        origin: String?,
        destination: String?,
        busAndTramJourneyCount: Int
    )

    fun trackSettingsClicked()

    fun trackAppInfoClicked()
}
