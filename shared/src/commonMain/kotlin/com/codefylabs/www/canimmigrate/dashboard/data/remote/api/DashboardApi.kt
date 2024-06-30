package com.codefylabs.www.canimmigrate.dashboard.data.remote.api

import com.codefylabs.www.canimmigrate.core.util.NetworkResult

interface DashboardApi {

    suspend fun getTrendingNews(): NetworkResult
    suspend fun getNews(page : Int, size : Int, category : String) : NetworkResult

}