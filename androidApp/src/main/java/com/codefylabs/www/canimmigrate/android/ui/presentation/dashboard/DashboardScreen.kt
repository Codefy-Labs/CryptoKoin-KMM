package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.core.navigation.navigateToAuthentication
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.BottomNavigationBar
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.onboarding.navigateToOnBoarding
import com.codefylabs.www.canimmigrate.android.ui.presentation.auth.login.navigateToLoginScreen
import com.codefylabs.www.canimmigrate.android.ui.presentation.blogdetail.navigateToBlogDetail
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.csr.CSR_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.csr.csrScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds.FEEDS_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.feeds.feedsScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process.PROCESS_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process.processScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.profile.PROFILE_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.profile.profileScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.programs.PROGRAMS_NAV_ROUTE
import com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.programs.programsScreenRoute
import com.codefylabs.www.canimmigrate.android.ui.presentation.splash.SPLASH_NAV_ROUTE
import com.codefylabs.www.canimmigrate.dashboard.presentation.DashboardEvent
import com.codefylabs.www.canimmigrate.dashboard.presentation.DashboardSharedVM
import org.koin.androidx.compose.koinViewModel

const val DASHBOARD_NAV_ROUTE = "DASHBOARD_NAV_ROUTE"

fun NavHostController.navigateToDashboardScreen(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(SPLASH_NAV_ROUTE) {
            inclusive = true
        }
    },
) {
    navigate(route = DASHBOARD_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.dashboardScreenRoute(navHostController: NavHostController) {
    composable(route = DASHBOARD_NAV_ROUTE) {
        DashboardScreen(navigateToBlogDetail = {
            navHostController.navigateToBlogDetail()
        }, navigateToOnboardingSurvey = {
            navHostController.navigateToOnBoarding()
        }, navigateToLogin = {
            navHostController.navigateToAuthentication()
        })
    }
}

@Composable
private fun DashboardScreen(
    navigateToBlogDetail: (String) -> Unit,
    navigateToOnboardingSurvey: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: DashboardSharedVM = koinViewModel(),
) {

    OnEvent(event = viewModel.event) {
        when (it) {

            DashboardEvent.OpenOnboardingSurvey -> navigateToOnboardingSurvey()
            is DashboardEvent.Success -> Unit
            is DashboardEvent.Error -> Unit
        }
    }

    val bottomNavController = rememberNavController()
    val items = listOf(
        Triple(FEEDS_NAV_ROUTE, "Feeds", R.drawable.ic_feeds),
        Triple(PROCESS_NAV_ROUTE, "Process", R.drawable.ic_process),
        Triple(CSR_NAV_ROUTE, "CSR", R.drawable.ic_csr),
        Triple(PROGRAMS_NAV_ROUTE, "Programs", R.drawable.ic_programs),
        Triple(PROFILE_NAV_ROUTE, "Profile", R.drawable.ic_profile)
    )

    Column {
        NavHost(
            navController = bottomNavController,
            startDestination = FEEDS_NAV_ROUTE,
            modifier = Modifier.weight(1f)
        ) {
            feedsScreenRoute(navigateToBlogDetail = navigateToBlogDetail)
            processScreenRoute()
            csrScreenRoute()
            programsScreenRoute()
            profileScreenRoute(navigateToLogin = navigateToLogin)
        }
        Surface(shadowElevation = 4.dp) {
            BottomNavigationBar(navController = bottomNavController, items = items)
        }
    }

}