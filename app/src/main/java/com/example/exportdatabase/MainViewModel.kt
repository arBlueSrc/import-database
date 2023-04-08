package com.example.exportdatabase

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.exportdatabase.database.User
import com.example.exportdatabase.database.UserDatabase
import com.example.exportdatabase.database.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private val _responseReadAllDate = MutableLiveData<List<User>>()
    val responseReadAllDate: LiveData<List<User>>
        get() = _responseReadAllDate
    private val repository: UserRepository
    private var db:UserDatabase? = null

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        db = UserDatabase.getDatabase(application)
        repository = UserRepository(userDao)
getUsers()
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun getUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            _responseReadAllDate.postValue(
                repository.readAlldata()
            )
        }

    }


    fun backUpDataBase(context :Context):Int{
       return db?.backupDatabase(context) ?: -1
    }
}