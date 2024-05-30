package com.codefylabs.www.cryptokoin.android.home.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codefylabs.www.cryptokoin.core.util.asCurrencyWith2Decimals
import com.codefylabs.www.cryptokoin.home.domain.models.Coin

@Composable
fun CoinRowItem(coin: Coin, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${coin.rank}", style = MaterialTheme.typography.labelLarge)

        Image(
            painter = rememberAsyncImagePainter(model = coin.image),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(28.dp)
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
                text = "${coin.priceChangePercentage24h}%",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if ((coin.priceChangePercentage24h ?: 0.0) >= 0
                    ) Color.Green else Color.Red
                )
            )
        }
    }
}