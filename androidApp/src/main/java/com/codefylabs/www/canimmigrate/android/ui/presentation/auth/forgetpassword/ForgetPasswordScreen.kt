package com.codefylabs.www.canimmigrate.android.ui.presentation.auth.forgetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

private const val FORGET_PASSWORD_NAV_ROUTE = "FORGET_PASSWORD_NAV_ROUTE"

fun NavHostController.navigateToForgetPasswordScreen(navOptions: NavOptions? = null) {
    navigate(route = FORGET_PASSWORD_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.forgetPasswordScreenRoute(
    navigateUp: () -> Unit,
) {
    composable(route = FORGET_PASSWORD_NAV_ROUTE) {
        ForgetPasswordScreen(
            navigateUp = navigateUp,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(navigateUp: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



        }
    }
}

