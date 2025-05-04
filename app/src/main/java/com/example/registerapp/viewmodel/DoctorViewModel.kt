package com.example.registerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.registerapp.doctorDatabase.AppDatabase
import com.example.registerapp.doctorDatabase.Appointment
import com.example.registerapp.doctorDatabase.Doctor
import com.example.registerapp.doctorDatabase.DoctorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class DoctorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DoctorRepository
    val allDoctors: LiveData<List<Doctor>>

    init {
        val dao = AppDatabase.getDatabase(application).doctorDao()
        repository = DoctorRepository(dao)
        allDoctors = repository.allDoctors.asLiveData()
    }


    // دالة لإدخال دكتور جديد
    fun insertDoctor(doctor: Doctor) {
        viewModelScope.launch {
            repository.insertDoctor(doctor)
        }
    }
    fun getDoctorByIdLive(doctorId: Int): LiveData<Doctor> {
        return repository.getDoctorByIdLive(doctorId)
    }
    fun getDoctorById(id: Int) {
        viewModelScope.launch {
            repository.getDoctorById(id)
        }
    }
    fun bookAppointment(doctor: Doctor, time: String, day: String) {

        val appointment = Appointment(
            doctorId = doctor.id,
            doctorName = doctor.name,
            specialty = doctor.specialty,
            time = time,
            date = "2025-05-02",
            day = day

        )
        viewModelScope.launch {
            repository.insertAppointment(appointment)
        }
    }
}

