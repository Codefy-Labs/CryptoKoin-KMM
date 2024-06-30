package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codefylabs.www.canimmigrate.dashboard.domain.models.news.News


@Composable
fun NewsListUI(
    newsList: List<News>,
    perPage: Int,
    isLoading: Boolean,
    onLoadMoreItem: () -> Unit
) {
    var previousItemCount by remember { mutableStateOf(perPage) }
    val scrollState = rememberLazyListState()

    // Observe the scroll position
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
            .collect { lastVisibleIndex ->
                val totalItems = scrollState.layoutInfo.totalItemsCount
                if (totalItems - lastVisibleIndex <= 2 && totalItems >= previousItemCount) {
                    previousItemCount = totalItems
                    onLoadMoreItem()
                }
            }
    }

    LazyColumn(state = scrollState) {
        // Items
        items( items = newsList) {

        }

        // Footer
        if (isLoading)
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
    }
}