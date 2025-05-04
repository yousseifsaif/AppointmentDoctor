package com.example.registerapp.doctorDatabase


import androidx.lifecycle.LiveData
import com.example.registerapp.doctorDatabase.Appointment
import com.example.registerapp.doctorDatabase.AppointmentDao
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val appointmentDao: AppointmentDao) {
    val appointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()

    suspend fun insertAppointment(appointment: Appointment) {
        appointmentDao.insertAppointment(appointment)
    }

    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.deleteAppointment(appointment)
    }
}