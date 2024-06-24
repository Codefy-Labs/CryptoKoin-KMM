package com.codefylabs.www.canimmigrate.dashboard.domain.repo


interface DashboardRepository {
    suspend fun getTrendingNews(): List<String>
}