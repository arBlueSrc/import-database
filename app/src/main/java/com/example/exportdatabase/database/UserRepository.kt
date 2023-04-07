package com.example.exportdatabase.database

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAlldata: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}