package com.example.exportdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.exportdatabase.database.User
import com.example.exportdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.submit.setOnClickListener {
            addUser()
        }

        binding.export.setOnClickListener {
            val result = viewModel.backUpDataBase(this)
            Toast.makeText(this,"result: ${result}",Toast.LENGTH_LONG).show()
        }
    }

    private fun addUser() {
        val user = User(
            id = 0,
            firstName = binding.name.text.toString(),
            lastName = binding.lastName.text.toString(),
            age = binding.Age.text.toString().toInt()
        )

        viewModel.addUser(user)

        Toast.makeText(this,"user added!",Toast.LENGTH_LONG).show()
    }
}