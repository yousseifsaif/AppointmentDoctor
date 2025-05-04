package com.example.registerapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private val prefs = app.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    var isLoggedIn = mutableStateOf(prefs.getBoolean("isLoggedIn", false))
        private set

    fun login() {
        prefs.edit { putBoolean("isLoggedIn", true) }
        isLoggedIn.value = true
    }

    fun logout() {
        prefs.edit { putBoolean("isLoggedIn", false) }
        isLoggedIn.value = false
    }
}
