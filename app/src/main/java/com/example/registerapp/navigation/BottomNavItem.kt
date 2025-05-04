package com.example.registerapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object DoctorList : BottomNavItem("doctorList", "Doctors", Icons.Default.Home)
    object Appointments : BottomNavItem("appointments", "Appointments", Icons.Default.DateRange)
}