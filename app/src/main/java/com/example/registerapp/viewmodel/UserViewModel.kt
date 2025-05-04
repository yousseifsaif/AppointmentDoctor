package com.example.registerapp.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.registerapp.registerDatabase.UserEntity
import com.example.registerapp.registerDatabase.AppDatabase
import com.example.registerapp.registerDatabase.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.Companion.getDatabase(application).userDao()
    private val repository = UserRepository(dao)

    var registerState by mutableStateOf("")
        private set

    fun registerUser(username: String, email: String, password: String, rePassword: String, mobile: String) {
        viewModelScope.launch {
            when {
                username.isBlank() || email.isBlank() || password.isBlank() || rePassword.isBlank() || mobile.isBlank() -> {
                    registerState = "all fields required!"
                }
                password != rePassword -> {
                    registerState = "Password doesn't match"
                }
                !isValidGmail(email) -> {
                    registerState = "Only Gmail Accounts"
                }
                else -> {
                    val success = repository.register(
                        UserEntity(
                            username = username,
                            email = email,
                            password = password,
                            mobile = mobile
                        )
                    )
                    registerState = if (success) "Registered Successfully!" else "user already exists!"
                }
            }
        }
    }
    private fun isValidGmail(email: String): Boolean {
        val gmailRegex = Regex("^[A-Za-z0-9._%+-]+@gmail\\.(com|\\w{2,})$", RegexOption.IGNORE_CASE)
        return gmailRegex.matches(email)
    }
    var loginState by mutableStateOf("")
        private set
    fun loginUser(input: String, password: String) {

        viewModelScope.launch {

            if (input.isBlank() || password.isBlank()) {
                loginState = "all fields required!"
            } else {
                val success = repository.login(input, password)
                loginState = if (success) "Login Successfully!" else "Invalid Data!!"
            }
        }
    }

    fun clearLoginState() {
        loginState = ""
    }

    fun clearState() {
        registerState = ""
    }

}