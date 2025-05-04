package com.example.registerapp.utils

import android.content.Context
import com.example.registerapp.doctorDatabase.Doctor
import com.example.registerapp.viewmodel.DoctorViewModel
import com.example.registerapp.viewmodel.DoctorViewModelFactory

fun preloadDoctorsIfNeeded(context: Context, doctorViewModelFactory: DoctorViewModelFactory) {
    val sharedPrefs = context.getSharedPreferences("init_prefs", Context.MODE_PRIVATE)
    val isFirstRun = sharedPrefs.getBoolean("first_run", true)

    if (isFirstRun) {
        val tempViewModel: DoctorViewModel =
            androidx.lifecycle.ViewModelProvider(
                context as androidx.lifecycle.ViewModelStoreOwner,
                doctorViewModelFactory
            )[DoctorViewModel::class.java]

        val json = context.assets.open("doctors.json").bufferedReader().use { it.readText() }
        val gson = com.google.gson.Gson()
        val doctorListType = object : com.google.gson.reflect.TypeToken<List<Doctor>>() {}.type
        val dummyDoctors: List<Doctor> = gson.fromJson(json, doctorListType)

        dummyDoctors.forEach { tempViewModel.insertDoctor(it) }

        sharedPrefs.edit().putBoolean("first_run", false).apply()
    }
}
