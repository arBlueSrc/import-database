package com.example.exportdatabase.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepository(private val userDao: UserDao) {

    suspend fun readAlldata(): List<User> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}