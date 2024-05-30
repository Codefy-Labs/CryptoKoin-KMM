package com.codefylabs.www.spider.android.core.navigation

sealed class GraphRoute(val route: String) {
    object Auth : GraphRoute(route = "auth_route")
    object Home : GraphRoute(route = "home_route")
}

sealed class AuthDestination(val route: String) {
    object Launcher : AuthDestination(route = "launcher_route")
    object Login : AuthDestination(route = "login_route")
    object SignUp : AuthDestination(route = "sign_up_route")
    object ForgetPassword : AuthDestination(route = "forget_password")
    object ResetPasswordScreen : AuthDestination(route = "reset_password/{email}")
}

sealed class HomeDestination(val route: String) {
    object Dashboard : HomeDestination(route = "dashboard_route")

}