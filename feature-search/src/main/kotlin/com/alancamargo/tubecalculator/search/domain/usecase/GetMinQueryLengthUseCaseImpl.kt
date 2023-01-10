package com.alancamargo.tubecalculator.search.domain.usecase

import com.alancamargo.tubecalculator.core.remoteconfig.RemoteConfigManager
import javax.inject.Inject

private const val REMOTE_CONFIG_KEY = "min_query_length"

internal class GetMinQueryLengthUseCaseImpl @Inject constructor(
    private val remoteConfigManager: RemoteConfigManager
) : GetMinQueryLengthUseCase {

    override fun invoke(): Int {
        return remoteConfigManager.getInt(REMOTE_CONFIG_KEY)
    }
}
