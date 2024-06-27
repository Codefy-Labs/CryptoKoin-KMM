package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.base.toast
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProfileEvent
import com.codefylabs.www.canimmigrate.dashboard.presentation.ProfileSharedVM
import org.koin.androidx.compose.koinViewModel

const val PROFILE_NAV_ROUTE = "PROFILE_NAV_ROUTE"

fun NavHostController.navigateToProfileScreen(navOptions: NavOptions? = null) {
    navigate(route = PROFILE_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.profileScreenRoute(navigateToLogin: () -> Unit) {
    composable(route = PROFILE_NAV_ROUTE) {
        ProfileScreen(navigateToLogin = navigateToLogin)
    }
}


@Composable
private fun ProfileScreen(
    navigateToLogin: () -> Unit,
    viewModel: ProfileSharedVM = koinViewModel(),
) {
    val lazyColumnState = rememberScrollState()

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    OnEvent(event = viewModel.event) {
        when (it) {
            is ProfileEvent.Error -> context.toast(it.error)
            is ProfileEvent.Success -> context.toast(it.message)
        }
    }

    Scaffold(topBar = {
        TopBar(title = "Profile", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }, contentWindowInsets = WindowInsets(bottom = 0)) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(lazyColumnState)
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            AnimatedVisibility(visible = state.session == null) {
                Button(onClick = navigateToLogin) {
                    Text(text = "Login Now")
                }
            }

            AnimatedVisibility(visible = state.session != null) {
                ProfileBody(
                    name = state.session?.name.toString(),
                    email = state.session?.email.toString(),
                    profilePictureUrl = state.session?.profilePicture,
                    profilePicturePlaceholder = state.session?.getNameInitials().toString(),
                    onEdit = { })
            }

            AnimatedVisibility(visible = state.session != null) {
                Column {
                    Divider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                    )
                    OptionItem("Delete My Account", R.drawable.ic_delete, onClick = {

                    })
                }
            }

            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

            OptionItem("Feedback", R.drawable.ic_feedback, onClick = {

            })
            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

            OptionItem("Tasks", R.drawable.ic_calendar, onClick = {

            })
            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))

            OptionItem("Support", R.drawable.ic_headphone, onClick = {

            })
            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))


            AnimatedVisibility(visible = state.session != null) {
                Column {
                    OptionItem("Logout", R.drawable.ic_logout, onClick = viewModel::logout)
                    Divider(
                        thickness = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                    )
                }
            }

        }
    }
}


@Composable
private fun ProfileBody(
    name: String,
    email: String,
    profilePictureUrl: String?,
    profilePicturePlaceholder: String,
    onEdit: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        ProfilePicture(
            placeholder = profilePicturePlaceholder,
            profilePictureUrl = profilePictureUrl
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = email,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onEdit,
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


    }
}

@Composable
fun ProfilePicture(placeholder: String, profilePictureUrl: String?) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
            .clip( MaterialTheme.shapes.medium),
        contentAlignment = Alignment.Center
    ) {
        if (profilePictureUrl.isNullOrEmpty()) {
            Text(text = placeholder, color = Color.White, style = TextStyle(fontSize = 24.sp))
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = profilePictureUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Composable
fun OptionItem(
    title: String,
    imgResId: Int,
    onClick: () -> Unit,
    iconColor: Color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 16.dp),
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