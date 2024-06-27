package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun LazyListScope.FilterRow(selected: String, filters: List<String>, onClick: (String) -> Unit) {

    items(items = filters, key = { it }) { button ->
        val isSelected = selected == button
        Text(
            text = button,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Black,
            ),
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .background(
                    color = if (isSelected) Color.Black else Color.LightGray.copy(0.5f),
                    shape = MaterialTheme.shapes.large
                )
                .clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = { onClick(button)})
                .padding(horizontal = 14.dp, vertical = 5.dp)

        )
    }

}
