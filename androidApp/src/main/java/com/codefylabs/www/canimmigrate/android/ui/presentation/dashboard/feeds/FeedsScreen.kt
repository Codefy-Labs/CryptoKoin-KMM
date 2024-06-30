package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.NewsCard
import com.codefylabs.www.canimmigrate.android.ui.components.NewsPagingListUI
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.ActionButton
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds.components.FilterRow
import com.codefylabs.www.canimmigrate.dashboard.presentation.HomeSharedViewModel
import org.koin.androidx.compose.koinViewModel

const val FEEDS_NAV_ROUTE = "FEEDS_NAV_ROUTE"

fun NavHostController.navigateToFeedsScreen(navOptions: NavOptions? = null) {
    navigate(route = FEEDS_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.feedsScreenRoute(
    navigateToNewsDetail: (String) -> Unit,
) {
    composable(route = FEEDS_NAV_ROUTE) {
        FeedsScreen(navigateToNewsDetail)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FeedsScreen(
    navigateToNewsDetail: (String) -> Unit,
    viewModel: HomeSharedViewModel = koinViewModel(),
) {
    val scrollState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val state by viewModel.state.collectAsState()

    // Observe the scroll position
    var previousItemCount by remember { mutableStateOf(state.news.pageCount) }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
            .collect { lastVisibleIndex ->
                val totalItems = scrollState.layoutInfo.totalItemsCount
                if (totalItems - lastVisibleIndex <= 2 && totalItems >= previousItemCount) {
                    previousItemCount = totalItems
                    viewModel.loadNews()
                }
            }
    }

    Scaffold(topBar = {
        TopBar(title = "Feeds", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }, contentWindowInsets = WindowInsets(bottom = 0)) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .padding(innerPadding),
            state = scrollState
        ) {

            if (state.trendingFeeds.isNotEmpty()) {
                item("TrendingNews") {

                    Text(
                        text = "Trending",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 20.dp)
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(items = state.trendingFeeds, key = { it.id }) {
                            NewsCard(
                                item = it,
                                modifier = Modifier
                                    .width((screenWidth.minus(40) / 2).dp)
                                    .animateItemPlacement(),
                                onClick = {
                                    navigateToNewsDetail(it.id.toString())
                                }
                            )
                        }
                    }
                }
            }

            item {
                LazyRow(contentPadding = PaddingValues(8.dp)) {
                    FilterRow(
                        state.selectedFilter,
                        filters = state.filters,
                        onClick = viewModel::selectFilter
                    )
                }
            }

            NewsPagingListUI(
                news = state.news.data,
                isLoading = state.news.isRefreshing,
                error = state.news.error,
                onClickNews = {
                    navigateToNewsDetail(it.id.toString())
                })

        }
    }
}






