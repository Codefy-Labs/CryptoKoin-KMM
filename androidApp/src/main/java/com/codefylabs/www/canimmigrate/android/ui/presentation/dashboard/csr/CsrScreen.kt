package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.csr

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.ActionButton
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar


const val CSR_NAV_ROUTE = "CSR_NAV_ROUTE"

fun NavHostController.navigateToCsrScreen(navOptions: NavOptions? = null) {
    navigate(route = CSR_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.csrScreenRoute() {
    composable(route = CSR_NAV_ROUTE) {
        CsrScreen()
    }
}

@Composable
private fun CsrScreen() {
    val lazyColumnState = rememberLazyListState()
    Scaffold(topBar = {
        TopBar(title = "", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }) { innerPadding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding), state = lazyColumnState) {

        }
    }
}

