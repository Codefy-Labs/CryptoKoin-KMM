package com.codefylabs.www.spider.android.home.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.codefylabs.www.spider.android.core.theme.PhoenixBackground
import com.codefylabs.www.spider.core.util.asCurrencyWith2Decimals
import com.codefylabs.www.spider.home.domain.models.Coin
import com.codefylabs.www.spider.home.presentation.home.HomeSharedViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: HomeSharedViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsState()

    PhoenixBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(
                query = state.searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = {},
                active = false,
                onActiveChange = {}, content = {

                }, placeholder = {
                    Text(text = "Search by name of symbol")
                })

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Coin", style = MaterialTheme.typography.labelLarge)
                Text(text = "Price", style = MaterialTheme.typography.labelLarge)
            }

            LazyColumn {
                items(state.allCoins, key = { it.id }) { item ->
                    CoinRowItem(coin = item)
                }
            }

        }
    }

}

@Composable
private fun CoinRowItem(coin: Coin) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${coin.rank}", style = MaterialTheme.typography.labelLarge)

        Image(
            painter = rememberAsyncImagePainter(model = coin.image),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(42.dp)
                .clip(CircleShape)
        )

        Text(text = coin.symbol.uppercase(), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            Text(
                text = coin.currentPrice.asCurrencyWith2Decimals(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = coin.priceChangePercentage24h.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}