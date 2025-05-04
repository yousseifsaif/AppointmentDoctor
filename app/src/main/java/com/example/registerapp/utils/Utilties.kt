package com.example.registerapp.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.registerapp.doctorDatabase.Doctor
import com.example.registerapp.viewmodel.DoctorViewModel
import com.example.registerapp.viewmodel.DoctorViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun preloadDoctorsIfNeeded(
    owner: ViewModelStoreOwner,
    context: Context,
    doctorViewModelFactory: DoctorViewModelFactory
) {
    val sharedPrefs = context.getSharedPreferences("init_prefs", Context.MODE_PRIVATE)
    val isFirstRun = sharedPrefs.getBoolean("first_run", true)

    if (isFirstRun) {
        val tempViewModel: DoctorViewModel =
            ViewModelProvider(owner, doctorViewModelFactory)[DoctorViewModel::class.java]

        val json = context.assets.open("doctors.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val doctorListType = object : TypeToken<List<Doctor>>() {}.type
        val dummyDoctors: List<Doctor> = gson.fromJson(json, doctorListType)

        dummyDoctors.forEach { tempViewModel.insertDoctor(it) }

        sharedPrefs.edit().putBoolean("first_run", false).apply()
    }
}

