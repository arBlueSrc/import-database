package com.example.exportdatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.exportdatabase.database.User
import com.example.exportdatabase.database.UserDatabase
import com.example.exportdatabase.database.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository
    private var db:UserDatabase? = null

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        db = UserDatabase.getDatabase(application)
        repository = UserRepository(userDao)
        readAllData = repository.readAlldata
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun backUpDataBase(context :Context):Int{
       return db?.backupDatabase(context) ?: -1
    }
}