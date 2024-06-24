package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme

const val PROFILE_NAV_ROUTE = "PROFILE_NAV_ROUTE"

fun NavHostController.navigateToProfileScreen(navOptions: NavOptions? = null) {
    navigate(route = PROFILE_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.profileScreenRoute() {
    composable(route = PROFILE_NAV_ROUTE) {
        ProfileScreen()
    }
}

@Preview
@Composable
private fun PreviewProfileBody() {
    AppTheme {
        Surface {
            ProfileBody()
        }
    }
}

@Composable
private fun ProfileScreen() {
    val lazyColumnState = rememberScrollState()
    Scaffold(topBar = {
        TopBar(title = "Profile", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(lazyColumnState)
                .padding(innerPadding),
        ) {
            ProfileBody()
        }
    }
}


@Composable
private fun ProfileBody() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePicture()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ankit Angra",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "ankitangra@codefylabs.com",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Edit profile action */ },
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 2.dp),
            modifier = Modifier.height(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = "Edit profile", style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

        OptionItem("Delete My Account", R.drawable.ic_delete)
        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

        OptionItem("Feedback", R.drawable.ic_feedback)
        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

        OptionItem("Tasks", R.drawable.ic_calendar)
        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

        OptionItem("Support", R.drawable.ic_headphone)
        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

        OptionItem("Logout", R.drawable.ic_logout)
        Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

    }
}

@Composable
fun ProfilePicture() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "AG", color = Color.White, style = TextStyle(fontSize = 24.sp))
    }
}

@Composable
fun OptionItem(
    title: String,
    imgResId: Int,
    iconColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title, style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
            )
        )

        Icon(
            painter = painterResource(id = imgResId),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}