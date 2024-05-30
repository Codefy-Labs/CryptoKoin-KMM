package com.codefylabs.www.spider.android.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingBar(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    if (visible)
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp).align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
}

@Composable
fun LoadingBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp).align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }
}