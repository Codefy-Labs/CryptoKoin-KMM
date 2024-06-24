package com.codefylabs.www.canimmigrate.dashboard.data.remote.api

import com.codefylabs.www.canimmigrate.core.util.NetworkResult

interface DashboardApi {

    suspend fun getTrendingNews(): NetworkResult

}