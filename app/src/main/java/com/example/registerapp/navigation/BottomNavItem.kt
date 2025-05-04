package com.example.registerapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.registerapp.R

sealed class BottomNavItem(val route: String, val title: Int, val iconRes: Int) {
    object DoctorList : BottomNavItem("doctorList", R.string.doctors, R.drawable.doctor)
    object Appointments : BottomNavItem("appointments", R.string.appointments, R.drawable.calender)
}