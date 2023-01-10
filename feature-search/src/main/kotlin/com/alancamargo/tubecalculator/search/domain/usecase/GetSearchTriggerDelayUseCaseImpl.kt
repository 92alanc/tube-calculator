package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import javax.inject.Inject

private const val REMOTE_CONFIG_KEY = "search_trigger_delay_millis"

internal class GetSearchTriggerDelayUseCaseImpl @Inject constructor(
    private val remoteConfigManager: RemoteConfigManager
) : GetSearchTriggerDelayUseCase {

    override fun invoke(): Long {
        return remoteConfigManager.getLong(REMOTE_CONFIG_KEY)
    }
}
