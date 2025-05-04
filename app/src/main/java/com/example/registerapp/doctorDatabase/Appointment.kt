package com.example.registerapp.doctorDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "appointment_table")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val doctorId: Int,
    val doctorName: String,
    val date: String,
    val specialty: String,
    val time: String,
    val day: String


)