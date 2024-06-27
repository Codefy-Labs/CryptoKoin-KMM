package com.codefylabs.www.canimmigrate.android.ui.components

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun WebView(url: String) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .alpha(0.99f),
            factory = { ctx ->
                WebView(ctx).apply {
                    this.setWebChromeClient(object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView, progress: Int) {
                            isLoading = true

                            if (progress == 100) {
                                isLoading = false
                            }
                        }
                    })
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    loadUrl(url)
//                    this.setBackgroundColor(0x00000000);
                    // Disable user interaction
                    setOnTouchListener { _, _ -> true }
                    settings.javaScriptEnabled = false
                    settings.domStorageEnabled = false
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
        )

        AnimatedVisibility (isLoading) {
            Loader()
        }

    }


}