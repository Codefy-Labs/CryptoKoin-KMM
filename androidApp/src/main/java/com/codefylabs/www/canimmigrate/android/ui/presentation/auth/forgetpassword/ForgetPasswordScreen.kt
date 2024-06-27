package com.codefylabs.www.canimmigrate.android.ui.presentation.auth.forgetpassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.base.toast
import com.codefylabs.www.canimmigrate.android.ui.components.base.toastLong
import com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword.ForgetPasswordEvent
import com.codefylabs.www.canimmigrate.auth.presentation.forgetpassword.ForgetPasswordSharedVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

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
fun ForgetPasswordScreen(
    navigateUp: () -> Unit,
    viewModel: ForgetPasswordSharedVM = koinViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    OnEvent(event = viewModel.event) {
        when (it) {
            is ForgetPasswordEvent.Error -> context.toast(it.error)
            is ForgetPasswordEvent.OtpSent -> context.toast(it.message)
            is ForgetPasswordEvent.PasswordResetSuccess -> {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Reset Your Password",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = "Enter your registered email to receive an OTP for resetting your password",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChanged,
                    label = { Text("Email Id") },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    enabled = !state.isLoading && !state.isOtpSent,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )

                AnimatedVisibility(visible = state.isOtpSent) {
                    IconButton(onClick = {
                        viewModel.editEmail()
                        coroutine.launch {
                            delay(300)
                            focusRequester.requestFocus()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = state.isOtpSent) {
                Column {
                    OutlinedTextField(
                        value = state.otp,
                        onValueChange = viewModel::onOtpChanged,
                        label = { Text("Confirmation Code") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        enabled = !state.isLoading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = state.newPassword,
                        onValueChange = viewModel::onPasswordChanged,
                        label = { Text("New password") },
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
                        enabled = !state.isLoading,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                }
            }

            Button(
                onClick = viewModel::handleSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = !state.isLoading
            ) {
                Text(
                    text = "Submit",
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

        }
    }
}

