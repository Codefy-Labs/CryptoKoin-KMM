package com.codefylabs.www.cryptokoin.android.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.codefylabs.www.cryptokoin.android.home.presentation.home.DashboardScreen

fun NavGraphBuilder.HomeNavigation(
    navController: NavHostController,
    bottomLevelEvents: (Boolean) -> Unit
) {

    navigation(
        startDestination = HomeDestination.Dashboard.route,
        route = GraphRoute.Home.route
    ) {

        composable(HomeDestination.Dashboard.route) {
            DashboardScreen()
        }

    }

}

