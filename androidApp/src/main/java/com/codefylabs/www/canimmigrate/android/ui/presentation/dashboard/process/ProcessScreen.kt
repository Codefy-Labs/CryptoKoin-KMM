package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.ActionButton
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process.components.CitizenshipSection
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process.components.ExpressEntrySection
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import kotlinx.coroutines.launch

const val PROCESS_NAV_ROUTE = "PROCESS_NAV_ROUTE"

fun NavHostController.navigateToProcessScreen(navOptions: NavOptions? = null) {
    navigate(route = PROCESS_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.processScreenRoute() {
    composable(route = PROCESS_NAV_ROUTE) {
        ProcessScreen()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProcessScreen() {
    val coroutine = rememberCoroutineScope()
    val tabs = listOf("Express Entry", "Citizenship")
    val pagerState = rememberPagerState { tabs.size }

    Scaffold(topBar = {
        TopBar(title = "Process", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }, contentWindowInsets = WindowInsets(bottom = 0)) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(top = 8.dp),
                containerColor = MaterialTheme.colorScheme.background,
                divider = { }
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = pagerState.currentPage == index
                    Tab(selected = isSelected, onClick = {
                        coroutine.launch {
                            pagerState.scrollToPage(index)
                        }
                    }) {
                        Text(
                            text = tab, style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                color = if (isSelected) MaterialTheme.colorScheme.onSurface
                                else MaterialTheme.colorScheme.onSurface.copy(0.5f)
                            ),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
                when (it) {
                    0 -> ExpressEntrySection()
                    1 -> CitizenshipSection()
                    else -> Unit
                }
            }
        }

    }
}

@Preview
@Composable
private fun ProcessScreenPreview() {
    AppTheme {
        Surface {
            ProcessScreen()
        }
    }
}


