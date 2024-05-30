package com.codefylabs.www.cryptokoin.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

val LocalNavHostController =
    compositionLocalOf<NavHostController> { throw IllegalStateException("Not provided yet") }

@Composable
fun MainNavigation(
    modifier: Modifier,
    navHostController: NavHostController,
    bottomLevelEvents: (Boolean) -> Unit,
) {

    CompositionLocalProvider(LocalNavHostController provides navHostController) {
        NavHost(
            modifier = modifier,
            navController = navHostController,
            startDestination =  GraphRoute.Home.route
        ) {
            HomeNavigation(navHostController, bottomLevelEvents)
        }
    }

}