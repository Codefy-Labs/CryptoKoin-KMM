package com.codefylabs.www.canimmigrate.dashboard.domain.repo

import com.codefylabs.www.canimmigrate.dashboard.domain.models.news.NewsContainer


interface DashboardRepository {
    suspend fun getTrendingNews(): List<String>
    suspend fun getNews(page : Int, size : Int, category : String) : NewsContainer

}