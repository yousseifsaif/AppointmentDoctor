package com.example.registerapp.registerDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE (username = :input OR email = :input) AND password = :password")
    suspend fun loginUser(input: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username OR email = :email")
    suspend fun checkUserExists(username: String, email: String): UserEntity?
}
