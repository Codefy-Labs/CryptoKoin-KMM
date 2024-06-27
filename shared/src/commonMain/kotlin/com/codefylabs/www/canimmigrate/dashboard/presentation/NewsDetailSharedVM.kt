package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.data.DummyData
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.dashboard.domain.models.Feed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsDetailSharedVM(
    private val newsId: String,
) : StateViewModel<NewsDetailEvent, NewsDetailState>(NewsDetailState.initial()) {

    init {
        getNewsDetail(newsId)
    }

    private fun getNewsDetail(newsId: String) {
        coroutine.launch {
            updateState(NewsDetailState(isLoading = true))
            delay(2000)
            updateState(NewsDetailState(feed = DummyData.getTrendingFeedItems().first()))
        }
    }

}

sealed class NewsDetailEvent : Event {
    data class Success(val message: String) : NewsDetailEvent()
    data class Error(val error: String) : NewsDetailEvent()
}

data class NewsDetailState(
    val isLoading: Boolean = false,
    val feed: Feed? = null,
    val error: String? = null,
) : State {
    companion object {
        fun initial() = NewsDetailState()
    }

    val loaded: Boolean = feed != null && error == null && !isLoading
}


