package com.codefylabs.www.canimmigrate.android.ui.presentation.blogdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Scaffold
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
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.ActionButton
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar
import kotlinx.coroutines.delay


const val BLOG_DETAIL_NAV_ROUTE = "BLOG_DETAIL_NAV_ROUTE"

fun NavHostController.navigateToBlogDetail(navOptions: NavOptions? = null) {
    navigate(route = BLOG_DETAIL_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.blogDetailScreenRoute(
    onBackPressed: () -> Unit,
) {
    composable(route = BLOG_DETAIL_NAV_ROUTE) {
        BlogDetailScreen(onBackPressed = onBackPressed )
    }
}


@Composable
private fun BlogDetailScreen(
    onBackPressed: () -> Unit,
) {
    val lazyColumnState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopBar(title = "", onBackPress = onBackPressed, actions = {
                ActionButton(imageVector = Icons.Default.Share) {

                }
            })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding), state = lazyColumnState) {

        }
    }
}
