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
import com.example.registerapp.navigation.BottomNavItem // تأكد من استيراد هذا
import com.example.registerapp.navigation.MainScreen // استيراد الـ MainScreen الصحيح
import com.example.registerapp.screens.* // استيراد جميع الشاشات
import com.example.registerapp.ui.theme.RegisterAppTheme
import com.example.registerapp.viewmodel.*

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val appDatabase = AppDatabase.getDatabase(application)
        val appointmentDao = appDatabase.appointmentDao()
        val doctorDao = appDatabase.doctorDao()

        val appointmentRepository = AppointmentRepository(appointmentDao)

        val doctorViewModelFactory = DoctorViewModelFactory(application)
        val appointmentViewModelFactory = AppointmentViewModelFactory(appointmentRepository)

        preloadDoctorsIfNeeded(this, doctorViewModelFactory)

        setContent {
            RegisterAppTheme {
                val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                var isLoggedIn by rememberSaveable {
                    mutableStateOf(sharedPref.getBoolean("isLoggedIn", false))
                }

                val navController = rememberNavController()

                AppNavigation(
                    navController = navController,
                    isUserLoggedIn = isLoggedIn,
                    doctorViewModelFactory = doctorViewModelFactory,
                    appointmentViewModelFactory = appointmentViewModelFactory,
                    userViewModel = viewModel(),
                    onLoginSuccess = {
                        sharedPref.edit { putBoolean("isLoggedIn", true) }
                        isLoggedIn = true
                        navController.navigate(Graph.MAIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    },
                    onLogout = {
                        sharedPref.edit { putBoolean("isLoggedIn", false) }
                        isLoggedIn = false
                        navController.navigate(Graph.AUTH) {
                            popUpTo(Graph.MAIN) { inclusive = true }
                        }
                    })
            }
        }
    }

    private fun preloadDoctorsIfNeeded(
        context: Context, doctorViewModelFactory: DoctorViewModelFactory
    ) {
        val sharedPrefs = context.getSharedPreferences("init_prefs", Context.MODE_PRIVATE)
        val isFirstRun = sharedPrefs.getBoolean("first_run", true)

        if (isFirstRun) {
            val tempViewModel: DoctorViewModel =
                ViewModelProvider(this, doctorViewModelFactory)[DoctorViewModel::class.java]

            val json = context.assets.open("doctors.json").bufferedReader().use { it.readText() }
            val gson = com.google.gson.Gson()
            val doctorListType = object : com.google.gson.reflect.TypeToken<List<Doctor>>() {}.type
            val dummyDoctors: List<Doctor> = gson.fromJson(json, doctorListType)

            dummyDoctors.forEach {
                tempViewModel.insertDoctor(it)
            }

            sharedPrefs.edit { putBoolean("first_run", false) }
        }
    }

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

        NavHost(
            navController = navController, startDestination = startDestination
        ) {
            navigation(
                route = Graph.AUTH, startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(
                        viewModel = userViewModel,
                        onLoginSuccess = onLoginSuccess,
                        onNavigateToRegister = {
                            navController.navigate("register")
                        })
                }
                composable("register") {
                    RegisterScreen(
                        viewModel = userViewModel, onRegisterSuccess = {
                            val sharedPref = navController.context.getSharedPreferences(
                                "MyPrefs", Context.MODE_PRIVATE
                            )
                            sharedPref.edit().putBoolean("isLoggedIn", true).apply()
                            navController.navigate(Graph.MAIN) {
                                popUpTo(Graph.AUTH) {
                                    inclusive = true
                                }
                            }
                        })
                }
            }

            navigation(
                route = Graph.MAIN, startDestination = BottomNavItem.DoctorList.route
            ) {
                composable(BottomNavItem.DoctorList.route) {

                    val doctorViewModel: DoctorViewModel =
                        viewModel(factory = doctorViewModelFactory)
                    val appointmentViewModel: AppointmentViewModel =
                        viewModel(factory = appointmentViewModelFactory)
                    MainScreen(
                        navController = navController,
                        doctorViewModel = doctorViewModel,
                        appointmentViewModel = appointmentViewModel,
                        userViewModel = userViewModel,
                        onLogout = {
                            val sharedPref = navController.context.getSharedPreferences(
                                "MyPrefs", Context.MODE_PRIVATE
                            )
                            sharedPref.edit().putBoolean("isLoggedIn", false).apply()
                            navController.navigate("login") {
                                popUpTo("doctorList") { inclusive = true }
                            }
                        })

                }

            }
        }
    }
}

