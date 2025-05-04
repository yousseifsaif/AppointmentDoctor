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
import com.example.registerapp.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    var input by remember { mutableStateOf("") }
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
            TitleText(
                title = stringResource(R.string.Welcome),
                subtitle = stringResource(R.string.Logintocontinoue)
            )

            InputField(
                value = input,
                onValueChange = { input = it },
                label = stringResource(R.string.usernameorpasswordinvalid)
            )

            Spacer(modifier = Modifier.height(12.dp))

            InputField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            ActionButton(
                text = stringResource(R.string.login),
                onClick = { viewModel.loginUser(input, password) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text(stringResource(R.string.dontHaveAccount))
            }

            if (loginState.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = loginState,
                    color = if (loginState.contains("Done") || loginState.contains("Success")) Color(0xFF4CAF50) else Color.Red,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (loginState == "Login Successfully!") {
                LaunchedEffect(Unit) {
                    delay(1500)
                    val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().putBoolean("isLoggedIn", true).apply()
                    onLoginSuccess()
                    viewModel.clearLoginState()
                }
            }
        }
    }
}

@Composable
fun TitleText(title: String, subtitle: String) {
    Text(
        text = title,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = subtitle,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(text, fontSize = 16.sp)
    }
}
