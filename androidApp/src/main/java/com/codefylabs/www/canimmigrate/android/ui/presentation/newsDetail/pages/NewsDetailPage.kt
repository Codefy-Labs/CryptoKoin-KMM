package com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codefylabs.www.canimmigrate.android.ui.components.WebView
import com.codefylabs.www.canimmigrate.dashboard.domain.models.Feed

@Composable
fun NewsDetailPage(feed: Feed) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
    {
        when {
            feed.url.isNullOrEmpty() -> {

            }

            else -> {
                WebView(url = feed.url.toString())
            }
        }
    }
}