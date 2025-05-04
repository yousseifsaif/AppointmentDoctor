package com.example.registerapp.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registerapp.R
import com.example.registerapp.components.CustomTextInput
import com.example.registerapp.components.PrimaryButton
import com.example.registerapp.components.ScreenHeader
import com.example.registerapp.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState = viewModel.loginState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(
                title = stringResource(R.string.Welcome),
                subtitle = stringResource(R.string.Logintocontinoue)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextInput(
                value = username,
                onValueChange = { username = it },
                label = stringResource(R.string.username)
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextInput(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(text = stringResource(R.string.login)) {
                viewModel.loginUser(username, password)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text(stringResource(R.string.dontHaveAccount))
            }

            if (loginState.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = loginState,
                    color = if (loginState.contains("Success")) Color(0xFF4CAF50) else Color.Red,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (loginState == "Login Successfully!") {
                LaunchedEffect(Unit) {
                    delay(1500)
                    context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("isLoggedIn", true)
                        .apply()

                    onLoginSuccess()
                    viewModel.clearLoginState()
                }
            }
        }
    }
}

