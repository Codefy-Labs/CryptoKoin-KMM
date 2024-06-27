package com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.CustomIndicator
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsDetailTabs(
    pagerState: PagerState,
    tabs: List<Pair<Int, String?>>,
) {
    val coroutine = rememberCoroutineScope()

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = indicator,
            divider = {},
            contentColor = Color.White,
            containerColor = Color.Transparent,
        ) {
            tabs.forEachIndexed { index, data ->
                Tab(
                    modifier = Modifier.zIndex(6f),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutine.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    interactionSource = MutableInteractionSource(),
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = data.first),
                                contentDescription = "",
                                modifier = Modifier.size(18.dp)
                            )
                            data.second?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontSize = 14.sp,
                                        fontWeight = if (index == pagerState.currentPage) FontWeight.SemiBold else FontWeight.Medium,
                                    ),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                )
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewTabs() {
    val state = rememberPagerState { 2 }
    AppTheme {
        Surface {
            Column {
                NewsDetailTabs(
                    state,
                    listOf(R.drawable.ic_paper to null, R.drawable.ic_message to "Discussions")
                )
            }
        }
    }
}

