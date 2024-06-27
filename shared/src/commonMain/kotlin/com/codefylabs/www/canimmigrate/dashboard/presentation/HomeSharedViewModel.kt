package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.data.DummyData
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.dashboard.domain.models.Feed
import com.codefylabs.www.canimmigrate.dashboard.domain.usecases.TrendingNewsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeSharedViewModel(
    private val trendingNews: TrendingNewsUseCase,
) : StateViewModel<HomeEvent, HomeViewState>(HomeViewState.initial()) {


    init {
        fetchData()
    }

    private fun fetchData() {
        coroutine.launch {
            updateState(state.value.copy(isLoading = true))
//            delay(3000)
            updateState(
                state.value.copy(
                    isLoading = false,
                    trendingFeeds = DummyData.getTrendingFeedItems(),
                    feeds = DummyData.getTrendingFeedItems()
                )
            )
        }
    }

    fun selectFilter(filter: String) {
        updateState(state.value.copy(selectedFilter = filter))
    }


}

sealed class HomeEvent : Event {
    data class Success(val message: String) : HomeEvent()
    data class Error(val error: String) : HomeEvent()
}

data class HomeViewState(
    val trendingFeeds: List<Feed> = emptyList(),
    val feeds: List<Feed> = emptyList(),
    val isLoading: Boolean = true,
    val selectedFilter: String = "All",
    val filters: List<String> = listOf("All", "Policy Updates", "Legal Changes", "Success Stories"),
) : State {
    companion object {
        fun initial() = HomeViewState()
    }
}


