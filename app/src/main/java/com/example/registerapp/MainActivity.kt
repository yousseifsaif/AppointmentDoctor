package com.example.registerapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.content.edit // لتبسيط SharedPreferences edit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.registerapp.doctorDatabase.AppDatabase
import com.example.registerapp.doctorDatabase.AppointmentRepository
import com.example.registerapp.doctorDatabase.Doctor
import com.example.registerapp.navigation.AppNavigation
import com.example.registerapp.navigation.BottomNavItem // تأكد من استيراد هذا
import com.example.registerapp.navigation.MainScreen // استيراد الـ MainScreen الصحيح
import com.example.registerapp.screens.* // استيراد جميع الشاشات
import com.example.registerapp.ui.theme.RegisterAppTheme
import com.example.registerapp.utils.preloadDoctorsIfNeeded
import com.example.registerapp.viewmodel.*

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val appDatabase = AppDatabase.getDatabase(application)
        val appointmentRepository = AppointmentRepository(appDatabase.appointmentDao())

        val doctorViewModelFactory = DoctorViewModelFactory(application)
        val appointmentViewModelFactory = AppointmentViewModelFactory(appointmentRepository)

        preloadDoctorsIfNeeded(
            owner = this,
            context = this,
            doctorViewModelFactory = doctorViewModelFactory
        )
        setContent {
            RegisterAppTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel()
                val userViewModel: UserViewModel = viewModel()

                AppNavigation(
                    navController = navController,
                    isUserLoggedIn = mainViewModel.isLoggedIn.value,
                    doctorViewModelFactory = doctorViewModelFactory,
                    appointmentViewModelFactory = appointmentViewModelFactory,
                    userViewModel = userViewModel,
                    onLoginSuccess = mainViewModel::login,
                    onLogout = mainViewModel::logout
                )
            }
        }
    }


}

