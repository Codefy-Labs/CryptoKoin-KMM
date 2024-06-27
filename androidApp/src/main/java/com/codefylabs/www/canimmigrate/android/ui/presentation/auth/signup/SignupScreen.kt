package com.codefylabs.www.canimmigrate.android.ui.presentation.auth.signup

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.GoogleSignInButton
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.base.toast
import com.codefylabs.www.canimmigrate.android.ui.components.base.toastLong
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.auth.presentation.signup.SignUpSharedVM
import com.codefylabs.www.canimmigrate.auth.presentation.signup.SignupViewEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val SIGNUP_NAV_ROUTE = "SIGNUP_NAV_ROUTE"

fun NavHostController.navigateToSignupScreen(navOptions: NavOptions? = null) {
    navigate(route = SIGNUP_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.signupScreenRoute(
    navigateUp: () -> Unit,
    navigateToDashboard: () -> Unit,
) {
    composable(route = SIGNUP_NAV_ROUTE) {
        SignupScreen(
            navigateUp = navigateUp,
            navigateToDashboard = navigateToDashboard,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navigateUp: () -> Unit,
    navigateToDashboard: () -> Unit,
    viewModel: SignUpSharedVM = koinViewModel(),
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    OnEvent(event = viewModel.event) {
        when (it) {
            is SignupViewEvent.Error -> context.toast(it.error)
            is SignupViewEvent.VerificationEmailSent -> {
                context.toastLong(it.message)
                navigateUp()
            }

            is SignupViewEvent.GoogleSignUpSuccessful -> {
                context.toastLong(it.message)
                navigateToDashboard()
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
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_transparent),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Username TextField
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChanged,
                label = { Text("Full Name") },
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

            OutlinedTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChanged,
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                enabled = !state.isLoading
            )

            // Password TextField
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChanged,
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                enabled = !state.isLoading,
                supportingText = {
                    Text(
                        text = "*password at least 8 character with upper, lower digit & special character",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                        )
                    )
                }
            )

            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                enabled = !state.isLoading
            )


            // Continue Button
            Button(
                onClick = viewModel::signUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(12.dp),
                enabled = !state.isLoading
            ) {
                Text(
                    "Sign Up",
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

            GoogleSignInButton("SignUp with Google", isLoading = state.isGoogleSigning, onSuccess = { account ->
                account.idToken?.let { token ->
                    viewModel.signUpWithGoogle(token)
                } ?: context.toast("Unable to process!")
            }, onError = {
                context.toast(it)
            })

        }
    }
}


