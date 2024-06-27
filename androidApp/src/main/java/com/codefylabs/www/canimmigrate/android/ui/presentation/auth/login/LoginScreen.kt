package com.codefylabs.www.canimmigrate.android.ui.presentation.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.core.GoogleSignInHelper
import com.codefylabs.www.canimmigrate.android.ui.components.GoogleSignInButton
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.base.toast
import com.codefylabs.www.canimmigrate.android.ui.components.base.toastLong
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.auth.presentation.LoginEvent
import com.codefylabs.www.canimmigrate.auth.presentation.LoginSharedVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


const val LOGIN_NAV_ROUTE = "LOGIN_NAV_ROUTE"

fun NavHostController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    navigate(route = LOGIN_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.loginScreenRoute(
    navigateUp: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgetPassword: () -> Unit,
) {
    composable(route = LOGIN_NAV_ROUTE) {
        LoginScreen(
            navigateUp = navigateUp,
            navigateToSignUp = navigateToSignUp,
            navigateToForgetPassword = navigateToForgetPassword
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateUp: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgetPassword: () -> Unit,
    viewModel: LoginSharedVM = koinViewModel(),
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    OnEvent(event = viewModel.event) {
        when (it) {
            is LoginEvent.Error -> context.toast(it.error)
            is LoginEvent.NavigateToDashboard -> {
                context.toastLong(it.message)
                navigateUp()
            }
        }
    }

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
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_transparent), // Replace with the actual logo resource
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Username TextField
            OutlinedTextField(
                value = state.emailId,
                onValueChange = viewModel::onChangeEmailId,
                label = { Text("Email Id") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                enabled = !state.isLoading
            )

            // Password TextField
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onChangePassword,
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = if (state.passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    IconButton(onClick = viewModel::togglePasswordVisibility) {
                        Icon(
                            painter = painterResource(id = if (state.passwordVisibility) R.drawable.ic_eye else R.drawable.ic_eye_off),
                            contentDescription = null
                        )
                    }
                },
                singleLine = true,
                enabled = !state.isLoading
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ClickableText(
                    text = AnnotatedString("Forgot Password"),
                    onClick = { navigateToForgetPassword() },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            // Continue Button
            Button(
                onClick = viewModel::login,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = !state.isLoading
            ) {
                Text(
                    "Continue",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )

                AnimatedVisibility(
                    visible = state.isLoading,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                }
            }

            // Login with Google Button
            GoogleSignInButton(
                text = "Login with Google",
                isLoading = state.isGoogleSigning,
                onSuccess = { account ->
                    account.idToken?.let { token ->
                        viewModel.signInWithGoogle(token)
                    } ?: context.toast("Unable to process!")
                },
                onError = {
                    context.toast(it)
                })

            Spacer(modifier = Modifier.height(20.dp))

            // Sign Up Link
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Don’t have an account?", style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                ClickableText(
                    text = AnnotatedString("Sign Up"),
                    onClick = { navigateToSignUp() },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        Surface {
            LoginScreen({}, {}, {})
        }
    }
}
