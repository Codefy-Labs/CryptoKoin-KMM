package com.codefylabs.www.canimmigrate.dashboard.data.repo

import com.codefylabs.www.canimmigrate.core.util.NetworkResult
import com.codefylabs.www.canimmigrate.core.util.toObject
import com.codefylabs.www.canimmigrate.dashboard.data.local.DashboardPersistence
import com.codefylabs.www.canimmigrate.dashboard.data.remote.api.DashboardApi
import com.codefylabs.www.canimmigrate.dashboard.domain.repo.DashboardRepository
import kotlinx.serialization.json.Json

class DashboardRepositoryImpl(
    private val api: DashboardApi,
    private val json: Json,
    private val persistence: DashboardPersistence
) : DashboardRepository {
    override suspend fun getTrendingNews(): List<String> {
        return when (val result = api.getTrendingNews()) {
            is NetworkResult.Error -> throw result.error
            is NetworkResult.Success -> json.toObject<List<String>>(result.data.decodeToString())
        }
    }
}