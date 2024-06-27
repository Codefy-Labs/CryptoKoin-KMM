package com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.CustomIndicator
import com.codefylabs.www.canimmigrate.android.ui.components.LoaderFullScreen
import com.codefylabs.www.canimmigrate.android.ui.components.WarningBox
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.base.toast
import com.codefylabs.www.canimmigrate.android.ui.components.keyboardAsState
import com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.components.NewsDetailTabs
import com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.pages.DiscussionsPage
import com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.pages.NewsDetailPage
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.dashboard.presentation.DiscussionSharedVM
import com.codefylabs.www.canimmigrate.dashboard.presentation.NewsDetailEvent
import com.codefylabs.www.canimmigrate.dashboard.presentation.NewsDetailSharedVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


const val ARG_NEWS_ID = "ARG_NEWS_ID"
const val NEWS_DETAIL_NAV_ROUTE = "NEWS_DETAIL_NAV_ROUTE/{$ARG_NEWS_ID}"

fun NavHostController.navigateToNewsDetail(newsId: String, navOptions: NavOptions? = null) {
    navigate(
        route = NEWS_DETAIL_NAV_ROUTE.replace("{${ARG_NEWS_ID}}", newsId),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.newsDetailScreenRoute(navigateUp: () -> Unit) {
    composable(
        route = NEWS_DETAIL_NAV_ROUTE,
        arguments = listOf(navArgument(ARG_NEWS_ID) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val newsId = backStackEntry.arguments?.getString(ARG_NEWS_ID) ?: return@composable
        val viewModel: NewsDetailSharedVM = koinViewModel(parameters = { parametersOf(newsId) })
        val discussionViewModel: DiscussionSharedVM =
            koinViewModel(parameters = { parametersOf(newsId) })
        NewsDetailScreen(
            navigateUp = navigateUp,
            viewModel = viewModel,
            discussionViewModel = discussionViewModel
        )
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun NewsDetailScreen(
    navigateUp: () -> Unit,
    viewModel: NewsDetailSharedVM,
    discussionViewModel: DiscussionSharedVM,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val tabs = listOf(R.drawable.ic_paper to null, R.drawable.ic_message to "Discussions")
    val pagerState = rememberPagerState { tabs.size }
    val keyboardState by keyboardAsState()

    OnEvent(event = viewModel.event) {
        when (it) {
            is NewsDetailEvent.Error -> context.toast(it.error)
            is NewsDetailEvent.Success -> context.toast(it.message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(visible = state.loaded, enter = fadeIn(tween(1000))) {
                        NewsDetailTabs(pagerState = pagerState, tabs = tabs)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                   AnimatedVisibility(visible = state.loaded) {
                       IconButton(onClick = { }) {
                           Icon(imageVector = Icons.Default.Share, contentDescription = null)
                       }
                   }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        contentWindowInsets = WindowInsets(
            bottom = if (keyboardState) 0.dp
            else ScaffoldDefaults.contentWindowInsets.asPaddingValues()
                .calculateBottomPadding()
        )
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when {
                state.loaded && state.feed != null -> {
                    HorizontalPager(
                        state = pagerState, modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        when (it) {
                            0 -> NewsDetailPage(state.feed!!)
                            1 -> DiscussionsPage(viewModel = discussionViewModel)
                        }
                    }
                }

                state.isLoading -> {
                    LoaderFullScreen()
                }

                state.error != null -> {
                    WarningBox(message = state.error, onRetry = { })
                }
            }

        }
    }

}

