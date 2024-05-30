package com.codefylabs.www.spider.android.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.padding(16.dp)) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
    }

}