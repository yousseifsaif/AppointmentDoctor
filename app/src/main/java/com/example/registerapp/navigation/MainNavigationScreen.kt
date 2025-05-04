package com.example.registerapp.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registerapp.screens.AppointmentListScreen
import com.example.registerapp.screens.DoctorDetailsScreen
import com.example.registerapp.screens.DoctorListScreen
import com.example.registerapp.viewmodel.DoctorViewModel
import com.example.registerapp.viewmodel.AppointmentViewModel
import com.example.registerapp.viewmodel.UserViewModel


@Composable
fun MainScreen(
    navController: NavHostController,
    doctorViewModel: DoctorViewModel,
    appointmentViewModel: AppointmentViewModel,
    userViewModel: UserViewModel,
    onLogout: () -> Unit
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(bottomNavController) }
    ) { innerPadding ->
        NavHost(
            bottomNavController,
            startDestination = BottomNavItem.DoctorList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.DoctorList.route) {
                DoctorListScreen(
                    navController = bottomNavController,
                    doctorViewModel = doctorViewModel,
                    onLogout = onLogout
                )
            }

            composable(BottomNavItem.Appointments.route) {
                AppointmentListScreen(viewModel = appointmentViewModel)
            }
            composable("booking/{doctorId}") { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")?.toIntOrNull()
                doctorId?.let {
                    DoctorDetailsScreen(
                        doctorId = it,
                        doctorViewModel = doctorViewModel,
                        navController = bottomNavController
                    )
                }
            }
        }
    }
}
