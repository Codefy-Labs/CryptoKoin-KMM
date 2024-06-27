package com.codefylabs.www.canimmigrate.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codefylabs.www.canimmigrate.android.R

@Composable
fun LoaderLottie(
    animationResId: Int,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
    backgroundColor: Color = Color.Transparent
) {
    // Load the Lottie composition from the specified raw resource
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))

    // Animate the Lottie composition and loop it the specified number of times
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = iterations
    )

    // Container box for centering the animation and applying background color
    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        // Display the Lottie animation with a fixed size of 100.dp
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun Loader() {
    // A small loader with fixed size and no background color
    LoaderLottie(
        animationResId = R.raw.loading,
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun LoaderFullScreen() {
    // A full-screen loader with a semi-transparent dark gray background
    LoaderLottie(
        animationResId = R.raw.loading,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.LightGray.copy(alpha = 0.1f)
    )
}
