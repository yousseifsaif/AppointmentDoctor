package com.example.registerapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.registerapp.R

sealed class BottomNavItem(val route: String, val title: Int, val icon: ImageVector) {
    object DoctorList : BottomNavItem("doctorList", R.string.doctors, Icons.Default.Home)
    object Appointments : BottomNavItem("appointments", R.string.appointments, Icons.Default.DateRange)
}