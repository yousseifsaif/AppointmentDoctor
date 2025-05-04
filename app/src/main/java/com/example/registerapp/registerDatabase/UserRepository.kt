package com.example.registerapp.registerDatabase

class UserRepository(private val dao: UserDao) {

    suspend fun register(user: UserEntity): Boolean {
        val exists = dao.checkUserExists(user.username, user.email)
        if (exists != null) return false
        dao.registerUser(user)
        return true
    }

    suspend fun login(input: String, password: String): Boolean {
        return dao.loginUser(input, password) != null
    }
}
