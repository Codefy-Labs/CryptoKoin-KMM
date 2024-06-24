package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds


import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.codefylabs.www.canimmigrate.android.ui.components.BlogItemUi
import com.codefylabs.www.canimmigrate.android.ui.components.BlogItemWideUi
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.ActionButton
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds.components.FilterRow
import com.codefylabs.www.canimmigrate.core.data.DummyData
import com.codefylabs.www.canimmigrate.dashboard.presentation.HomeSharedViewModel
import org.koin.androidx.compose.koinViewModel

const val FEEDS_NAV_ROUTE = "FEEDS_NAV_ROUTE"

fun NavHostController.navigateToFeedsScreen(navOptions: NavOptions? = null) {
    navigate(route = FEEDS_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.feedsScreenRoute(
    navigateToBlogDetail: (String) -> Unit,
) {
    composable(route = FEEDS_NAV_ROUTE) {
        FeedsScreen(navigateToBlogDetail)
    }
}

@Composable
private fun FeedsScreen(
    navigateToBlogDetail: (String) -> Unit,
    viewModel: HomeSharedViewModel = koinViewModel(),
) {
    val lazyColumnState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var selectedButton by remember { mutableStateOf("All") }

    val buttons = listOf("All", "Policy Updates", "Legal Changes", "Success Stories")


    Scaffold(topBar = {
        TopBar(title = "Feeds", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = lazyColumnState
        ) {

            item {

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

                LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(8.dp)) {
                    items(items = DummyData.getTrendingFeedItems()) {
                        BlogItemUi(
                            item = it,
                            modifier = Modifier.width((screenWidth.minus(40) / 2).dp),
                            onClick = {
                                navigateToBlogDetail(it.key.toString())
                            }
                        )
                    }
                }

            }

            item {
                LazyRow(contentPadding = PaddingValues(8.dp)) {
                    FilterRow(selectedButton, filters = buttons, onClick = {
                        selectedButton = it
                    })
                }
            }

            items(items = DummyData.getTrendingFeedItems(), key = { it.key }) {
                BlogItemWideUi(item = it) {
                    navigateToBlogDetail(it.key.toString())
                }
            }

        }
    }
}






