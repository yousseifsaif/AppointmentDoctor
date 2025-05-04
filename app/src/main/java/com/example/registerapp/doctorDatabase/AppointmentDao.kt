package com.example.registerapp.doctorDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointment_table")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointment_table")
    suspend fun getAppointments(): List<Appointment> // لاسترجاعها مرة واحدة

    @Delete
    suspend fun deleteAppointment(appointment: Appointment) // حذف الموعد
}

