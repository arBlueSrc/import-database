package com.example.exportdatabase.test

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class DatabaseExporter (private val context: Context) {

    private val EXPORT_DIR = "/Alamrs"

    @RequiresApi(Build.VERSION_CODES.O)
    fun exportDatabase() {
        // Get the database path and create a File object from it
        val dbPath = context.getDatabasePath("user_database")
        val dbFile = File(dbPath.path)
        val dbFileshm = File(dbPath.path+"-shm")
        val dbFilewal = File(dbPath.path+"-wal")

        // Create the exported databases directory if it doesn't exist
        val exportDir = File("/data/user/0/com.example.exportdatabase/backupdatabases/")
//        val exportDir = File(Environment.getExternalStorageDirectory().absolutePath + EXPORT_DIR)
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        // Create a File object for the exported database
        val exportedDbFile = File("/data/user/0/com.example.exportdatabase/backupdatabases/", "user_database")
        val exportedDbFileShm = File("/data/user/0/com.example.exportdatabase/backupdatabases/", "user_database-shm")
        val exportedDbFileWal = File("/data/user/0/com.example.exportdatabase/backupdatabases/", "user_database-wal")

        // Copy the database file to the exported database file
        try {
            Log.i("TAG", "exportDatabase:try ")
            Log.i("TAG", "exportDatabase:${exportDir.exists()} ")
            Log.i("TAG", "exportDatabase:${dbFile.path} ")
            Log.i("TAG", "exportDatabase:${exportedDbFile.path} ")
            //dbFile.copyTo(exportedDbFile, true)
            copyFile(dbFile,exportedDbFile)
            copyFile(dbFileshm,exportedDbFileShm)
            copyFile(dbFilewal,exportedDbFileWal)
        } catch (e: Exception) {
            Log.i("TAG", "exportDatabase:catch ")
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun copyFile(src: File, dest: File) {
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }
}