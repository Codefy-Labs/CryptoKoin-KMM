package com.codefylabs.www.cryptokoin.android.home.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codefylabs.www.cryptokoin.android.core.theme.KmmAppBackground
import com.codefylabs.www.cryptokoin.android.home.presentation.home.components.CoinRowItem
import com.codefylabs.www.cryptokoin.android.home.presentation.home.components.ColumnTitleCoin
import com.codefylabs.www.cryptokoin.android.home.presentation.home.components.ColumnTitlePrice
import com.codefylabs.www.cryptokoin.android.home.presentation.home.components.SearchBarView
import com.codefylabs.www.cryptokoin.home.presentation.home.HomeSharedViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(viewModel: HomeSharedViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.sortOption) {
        coroutineScope.launch {
            lazyListState.scrollToItem(0)
        }
    }

    KmmAppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Live Prices",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold)
            )

            Spacer(modifier = Modifier.height(20.dp))

            SearchBarView(
                searchText = state.searchText,
                onSearchTextChanged = viewModel::onSearchTextChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                ColumnTitleCoin(
                    sortOption = state.sortOption,
                    onSortOptionChanged = viewModel::updateSortOptions
                )
                ColumnTitlePrice(
                    sortOption = state.sortOption,
                    onSortOptionChanged = viewModel::updateSortOptions
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                state = lazyListState,
                modifier = Modifier.weight(1f)
            ) {
                items(state.allCoins, key = { it.id }) { item ->
                    CoinRowItem(coin = item, modifier = Modifier.animateItemPlacement())
                }
            }

        }
    }

}




