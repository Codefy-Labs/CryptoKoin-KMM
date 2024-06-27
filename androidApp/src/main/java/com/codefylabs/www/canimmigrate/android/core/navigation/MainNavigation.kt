package com.codefylabs.www.canimmigrate.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.codefylabs.www.canimmigrate.android.ui.presentation.onboarding.onboardingScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.forgetpassword.forgetPasswordScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.forgetpassword.navigateToForgetPasswordScreen
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.login.LOGIN_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.login.loginScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.signup.navigateToSignupScreen
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.signup.signupScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.dashboardScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.navigateToDashboardScreen
import com.codefylabs.www.canimmigrate.android.ui.presentation.newsDetail.newsDetailScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.splash.SPLASH_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.splash.splashScreenRoute


@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH_NAV_ROUTE,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        splashScreenRoute(navController::navigateToDashboardScreen)
        dashboardScreenRoute(navController)
        onboardingScreenRoute(navController::navigateUp)
        authNavigation(navController)
        newsDetailScreenRoute(navigateUp = navController::navigateUp)
    }
}


private const val AUTH_NAVIGATION = "AUTH_NAVIGATION"

fun NavHostController.navigateToAuthentication() {
    navigate(AUTH_NAVIGATION)
}

fun NavGraphBuilder.authNavigation(navController: NavHostController) {
    navigation(startDestination = LOGIN_NAV_ROUTE, route = AUTH_NAVIGATION) {
        loginScreenRoute(
            navigateUp = navController::navigateUp,
            navigateToSignUp = navController::navigateToSignupScreen,
            navigateToForgetPassword = navController::navigateToForgetPasswordScreen
        )

        signupScreenRoute(navigateUp = navController::navigateUp, navigateToDashboard = {
            navController.popBackStack(AUTH_NAVIGATION, inclusive = true)
        })

        forgetPasswordScreenRoute(navigateUp = navController::navigateUp)
    }
}