package com.codefylabs.www.canimmigrate.android.ui.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Triple<String, String, Int>>,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = rememberAsyncImagePainter(model = screen.third),
                        contentDescription = screen.second,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        screen.second, style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (currentDestination?.route == screen.first) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.first } == true,
                onClick = {
                    navController.navigate(screen.first) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(

                )
            )
        }
    }
}