package com.codefylabs.www.cryptokoin.android.home.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.codefylabs.www.cryptokoin.home.presentation.home.SortOption


@Composable
fun ColumnTitleCoin(
    sortOption: SortOption,
    onSortOptionChanged: (SortOption) -> Unit
) {
    val iconOpacity by animateFloatAsState(
        if (sortOption == SortOption.Rank || sortOption == SortOption.RankReversed) 1f else 0f,
        label = "iconOpacity"
    )
    val iconRotation by animateFloatAsState(
        if (sortOption == SortOption.Rank) 0f else 180f,
        label = "iconOpacity"
    )

    Row(
        modifier = Modifier
            .clickable {
                onSortOptionChanged(if (sortOption == SortOption.Rank) SortOption.RankReversed else SortOption.Rank)
            },
    ) {
        Text("Coin", style = MaterialTheme.typography.labelLarge)
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Sort Icon",
            modifier = Modifier
                .alpha(iconOpacity)
                .rotate(iconRotation)
        )
    }
}


@Composable
fun ColumnTitlePrice(
    sortOption: SortOption,
    onSortOptionChanged: (SortOption) -> Unit
) {
    val iconOpacity by animateFloatAsState(
        if (sortOption == SortOption.Price || sortOption == SortOption.PriceReversed) 1f else 0f,
        label = "iconOpacity"
    )
    val iconRotation by animateFloatAsState(
        if (sortOption == SortOption.Price) 0f else 180f,
        label = "iconRotation"
    )

    Row(
        modifier = Modifier
            .clickable {
                onSortOptionChanged(if (sortOption == SortOption.Price) SortOption.PriceReversed else SortOption.Price)
            },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Price", style = MaterialTheme.typography.labelLarge)
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Sort Icon",
            modifier = Modifier
                .alpha(iconOpacity)
                .rotate(iconRotation)
        )
    }
}
