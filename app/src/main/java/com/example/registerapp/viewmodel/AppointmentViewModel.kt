package com.example.registerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.registerapp.doctorDatabase.AppDatabase
import com.example.registerapp.doctorDatabase.Appointment
import com.example.registerapp.doctorDatabase.AppointmentDao
import com.example.registerapp.doctorDatabase.AppointmentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {

    val appointments: LiveData<List<Appointment>> = repository.appointments.asLiveData()


    fun insertAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.insertAppointment(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.deleteAppointment(appointment)
        }
    }
}
