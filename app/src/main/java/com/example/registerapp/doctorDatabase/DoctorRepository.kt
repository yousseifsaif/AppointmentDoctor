package com.example.registerapp.doctorDatabase

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class DoctorRepository(private val doctorDao: DoctorDao) {

    val allDoctors: Flow<List<Doctor>> = doctorDao.getAllDoctors()

    suspend fun insertDoctor(doctor: Doctor) {
        doctorDao.insertDoctor(doctor)
    }

    suspend fun getDoctorById(id: Int): Doctor? {
        return doctorDao.getDoctorById(id)
    }
    fun getDoctorByIdLive(doctorId: Int): LiveData<Doctor> {
        return doctorDao.getDoctorByIdLive(doctorId)
    }
//    suspend fun getDoctors(): List<Doctor> {
//        return doctorDao.getAllDoctors()
//    }
suspend fun insertAppointment(appointment: Appointment) {
    doctorDao.insertAppointment(appointment)
}
}
