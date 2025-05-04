package com.example.registerapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.registerapp.screens.LoginScreen
import com.example.registerapp.screens.RegisterScreenWrapper
import com.example.registerapp.viewmodel.AppointmentViewModel
import com.example.registerapp.viewmodel.AppointmentViewModelFactory
import com.example.registerapp.viewmodel.DoctorViewModel
import com.example.registerapp.viewmodel.DoctorViewModelFactory
import com.example.registerapp.viewmodel.UserViewModel

object Graph {
    const val AUTH = "auth_graph"
    const val MAIN = "main_graph"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    isUserLoggedIn: Boolean,
    doctorViewModelFactory: DoctorViewModelFactory,
    appointmentViewModelFactory: AppointmentViewModelFactory,
    userViewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit
) {
    val startDestination = if (isUserLoggedIn) Graph.MAIN else Graph.AUTH

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(route = Graph.AUTH, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    viewModel = userViewModel,
                    onLoginSuccess = onLoginSuccess,
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }
            composable("register") {
                RegisterScreenWrapper(
                    viewModel = userViewModel,
                    onRegisterSuccess = onLoginSuccess
                )
            }
        }
        navigation(route = Graph.MAIN, startDestination = "doctorList") {
            composable("doctorList") {
                val doctorViewModel: DoctorViewModel = viewModel(factory = doctorViewModelFactory)
                val appointmentViewModel: AppointmentViewModel = viewModel(factory = appointmentViewModelFactory)
                MainScreen(
                    navController = navController,
                    doctorViewModel = doctorViewModel,
                    appointmentViewModel = appointmentViewModel,
                    userViewModel = userViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}
