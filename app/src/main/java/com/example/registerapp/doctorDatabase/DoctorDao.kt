package com.example.registerapp.doctorDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Query("SELECT * FROM doctor_table")
    fun getAllDoctors():  Flow<List<Doctor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: Doctor)

    @Query("SELECT * FROM doctor_table WHERE id = :doctorId")
    fun getDoctorByIdLive(doctorId: Int): LiveData<Doctor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)


    @Query("SELECT * FROM doctor_table WHERE id = :id LIMIT 1")
    suspend fun getDoctorById(id: Int): Doctor?
}


