package com.example.registerapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.registerapp.viewmodel.UserViewModel

@Composable
fun RegisterScreenWrapper(viewModel: UserViewModel, onRegisterSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    RegisterScreen(
        username = username,
        onUsernameChange = { username = it },
        email = email,
        onEmailChange = { email = it },
        mobile = mobile,
        onMobileChange = { mobile = it },
        password = password,
        onPasswordChange = { password = it },
        rePassword = rePassword,
        onRePasswordChange = { rePassword = it },
        registerState = viewModel.registerState,
        onRegisterClick = {
            viewModel.registerUser(username, email, password, rePassword, mobile)
        },
        onRegisterSuccess = onRegisterSuccess,
        clearRegisterState = { viewModel.clearState() }
    )
}
