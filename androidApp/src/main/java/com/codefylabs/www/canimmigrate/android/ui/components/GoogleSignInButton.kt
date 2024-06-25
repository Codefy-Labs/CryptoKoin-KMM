package com.codefylabs.www.canimmigrate.android.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.www.canimmigrate.android.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth


@Composable
  fun GoogleSignInButton(
    enabled: Boolean = true,
    onSuccess: (account: GoogleSignInAccount) -> Unit,
    onError: (String) -> Unit,
) {

    val context = LocalContext.current

    // Equivalent of onActivityResult
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                onSuccess.invoke(account)
            } catch (e: ApiException) {
                e.printStackTrace()
                onError(e.localizedMessage ?: e.message ?: "")
            }
        }

    val token = stringResource(id = R.string.default_web_client_id)

    OutlinedButton(
        onClick = {
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.4f)
        ),
        enabled = enabled
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google), // Replace with a suitable Google logo resource
            contentDescription = "Google Logo",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Login with Google", style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
        )
    }

}
