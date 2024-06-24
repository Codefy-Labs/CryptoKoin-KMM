package com.codefylabs.www.canimmigrate.android.ui.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.codefylabs.www.canimmigrate.android.R
import kotlinx.coroutines.delay

const val SPLASH_NAV_ROUTE = "SPLASHSCREEN"

fun NavHostController.navigateToSplashScreen(navOptions: NavOptions? = null) {
    navigate(route = SPLASH_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.splashScreenRoute(navigateToDashboard : () -> Unit) {
    composable(route = SPLASH_NAV_ROUTE) {
        SplashScreen(navigateToDashboard = navigateToDashboard)
    }
}


@Composable
private fun SplashScreen(navigateToDashboard : () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        navigateToDashboard()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
    }
}


