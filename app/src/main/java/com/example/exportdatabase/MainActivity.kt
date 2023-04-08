package com.example.exportdatabase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.exportdatabase.database.User
import com.example.exportdatabase.database.UserDatabase
import com.example.exportdatabase.databinding.ActivityMainBinding
import com.example.exportdatabase.test.DatabaseExporter
import de.raphaelebner.roomdatabasebackup.core.RoomBackup
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val backup = RoomBackup(this@MainActivity)
        val databaseExporter = DatabaseExporter(this)

        binding.submit.setOnClickListener {
            addUser()
        }

        binding.export.setOnClickListener {
//            val result = viewModel.backUpDataBase(this)
            databaseExporter.exportDatabase()
            //Toast.makeText(this,"result: done",Toast.LENGTH_LONG).show()
        }

        viewModel.responseReadAllDate.observe(this){
            binding.submit.text = viewModel.responseReadAllDate.value?.size.toString()
        }
    }

    private fun exportDatabase(backup: RoomBackup) {
        backup
            .database(UserDatabase.getDatabase(this@MainActivity))
            .enableLogDebug(true)
            .backupIsEncrypted(false)
            .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_DIALOG)
            .maxFileCount(5)
            .apply {
                onCompleteListener { success, message, exitCode ->
                    Log.d("TAG", "success: $success, message: $message, exitCode: $exitCode")
                    //if (success) restartApp(Intent(this@MainActivity, MainActivity::class.java))
                }
            }
            .backup()

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(
                        this@MainActivity,
                        "Permission denied to read your External storage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }



}