package com.codefylabs.www.canimmigrate.android.core.navigation

sealed class GraphRoute(val route: String) {
    object Home : GraphRoute(route = "home_route")
}


sealed class HomeDestination(val route: String) {
    object Dashboard : HomeDestination(route = "dashboard_route")

}