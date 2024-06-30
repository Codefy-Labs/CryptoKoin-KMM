package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.data.DummyData
import com.codefylabs.www.canimmigrate.core.domain.models.PagingState
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.dashboard.domain.models.Feed
import com.codefylabs.www.canimmigrate.dashboard.domain.models.news.News
import com.codefylabs.www.canimmigrate.dashboard.domain.models.news.NewsContainer
import com.codefylabs.www.canimmigrate.dashboard.domain.usecases.NewsUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class HomeSharedViewModel(
    private val newsUseCase: NewsUseCase,
) : StateViewModel<HomeEvent, HomeViewState>(HomeViewState.initial()) {

    private val mutex = Mutex()
    private var currentPage = 0

    init {
        refresh()
    }

    private fun loadTrendingNews() {
        coroutine.launch {
            updateState { it.copy(isLoading = true) }
            updateState {
                it.copy(
                    isLoading = false,
                    trendingFeeds = DummyData.getTrendingFeedItems(),
                )
            }
        }
    }

    private fun loadCategories() {

    }


    fun selectFilter(filter: String) {
        if (filter == stateValue.selectedFilter)
            return

        updateState {
            it.copy(selectedFilter = filter)

        }
    }

    fun refresh() {
        currentPage = 0
        updateState { it.copy(news = PagingState()) }
        loadNews()
        loadTrendingNews()
        loadCategories()
    }


    fun loadNews() {
        if (stateValue.news.isRefreshing || stateValue.news.isReachedEnd)
            return // Prevent concurrent refresh or load more


        coroutine.launch {
            try {
                mutex.withLock {
                    updateState { it.copy(news = it.news.copy(isRefreshing = true)) }

                    val newsContainer = fetchNewsWithRetry(currentPage)

                    when {
                        newsContainer == null -> {
                            updateState {
                                it.copy(
                                    news = PagingState(
                                        isReachedEnd = true,
                                        error = "Oops! Please try to refresh.."
                                    )
                                )
                            }
                        }

                        (newsContainer.articles.isNullOrEmpty() && currentPage == 0) -> {
                            updateState {
                                it.copy(
                                    news = PagingState(
                                        isReachedEnd = true,
                                        error = "There are currently no news updates available."
                                    )
                                )
                            }
                        }

                        else -> {
                            currentPage++
                            updateState {
                                it.copy(
                                    news = PagingState(
                                        data = it.news.data + (if (newsContainer.articles.isNullOrEmpty()) emptyList() else newsContainer.articles),
                                        isReachedEnd = newsContainer.isLastPage
                                    )
                                )
                            }
                        }

                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateState { it.copy(news = it.news.copy(error = "Oops! An error occurred")) }
            } finally {
                updateState { it.copy(news = it.news.copy(isRefreshing = false)) }
            }
        }
    }

    private suspend fun fetchNewsWithRetry(page: Int, retries: Int = 3): NewsContainer? {
        repeat(retries) {
            try {
                return when (val response =
                    newsUseCase.execute(category = state.value.selectedFilter, page = page)) {
                    is Either.Error -> throw Exception(response.message)
                    is Either.Success -> response.data
                }
            } catch (e: Exception) {
                if (it == retries - 1) throw e
            }
        }
        return null
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
    val news: PagingState<News> = PagingState(),
) : State {
    companion object {
        fun initial() = HomeViewState()
    }
}


