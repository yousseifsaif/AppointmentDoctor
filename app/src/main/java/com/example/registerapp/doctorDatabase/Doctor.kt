package com.example.registerapp.doctorDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@androidx.room.Entity(tableName = "doctor_table")
data class Doctor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String="",
    val specialty: String="",
    val availableDays: List<String> = emptyList(),
    val availableHours: String =""
)
class Converters {
    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(",")
    }
}