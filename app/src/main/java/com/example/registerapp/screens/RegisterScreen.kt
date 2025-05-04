package com.example.registerapp.screens

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
fun RegisterScreen(
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    mobile: String,
    onMobileChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    rePassword: String,
    onRePasswordChange: (String) -> Unit,
    registerState: String,
    onRegisterClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    clearRegisterState: () -> Unit
) {
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
                title = stringResource(R.string.createacc),
                subtitle = stringResource(R.string.Pleaseenteryourinformationcarefully)
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextInput(value = username, onValueChange = onUsernameChange, label = stringResource(R.string.username))
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextInput(value = email, onValueChange = onEmailChange, label = stringResource(R.string.email))
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextInput(value = mobile, onValueChange = onMobileChange, label = stringResource(R.string.mobile))
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextInput(value = password, onValueChange = onPasswordChange, label = stringResource(R.string.password), isPassword = true)
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextInput(value = rePassword, onValueChange = onRePasswordChange, label = stringResource(R.string.conpassword), isPassword = true)
            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = stringResource(R.string.createaccount),
                onClick = onRegisterClick
            )

            if (registerState.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = registerState,
                    color = if (registerState.contains(stringResource(R.string.done_2), ignoreCase = true) || registerState.contains(
                            stringResource(R.string.success), ignoreCase = true))
                        Color(0xFF4CAF50) else Color.Red,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (registerState == stringResource(R.string.registered_successfully)) {
                LaunchedEffect(Unit) {
                    delay(1500)
                    onRegisterSuccess()
                    clearRegisterState()
                }
            }
        }
    }
}

