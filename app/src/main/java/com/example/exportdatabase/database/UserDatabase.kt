package com.example.exportdatabase.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File
import java.io.IOException

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {



    abstract fun userDao(): UserDao

    companion object {
        //@Volatile
        private var INSTANCE:UserDatabase? = null

        const val THETABLE_TABLENAME = "theTable"
        const val THETABLE_ID_COLUMN = THETABLE_TABLENAME + "_id"
        const val TheTABLE_OTHER_COLUMN = THETABLE_TABLENAME + "_other"
        const val THEDATABASE_DATABASE_NAME = "user_database"
        const val THEDATABASE_DATABASE_BACKUP_SUFFIX = "-bkp"
        const val SQLITE_WALFILE_SUFFIX = "-wal"
        const val SQLITE_SHMFILE_SUFFIX = "-shm"

        fun getDatabase(context: Context):UserDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }





    /**
     * Backup the database
     */
    fun backupDatabase(context: Context): Int {
        var result = -99
        if (INSTANCE==null) return result

        val dbFile = context.getDatabasePath(THEDATABASE_DATABASE_NAME)
        val dbWalFile = File(dbFile.path + SQLITE_WALFILE_SUFFIX)
        val dbShmFile = File(dbFile.path + SQLITE_SHMFILE_SUFFIX)
//        val bkpFile = File(dbFile.path + THEDATABASE_DATABASE_BACKUP_SUFFIX)
        val bkpFile = File(dbFile.path + THEDATABASE_DATABASE_BACKUP_SUFFIX)
        val bkpWalFile = File(bkpFile.path + SQLITE_WALFILE_SUFFIX)
        val bkpShmFile = File(bkpFile.path + SQLITE_SHMFILE_SUFFIX)
        if (bkpFile.exists()) bkpFile.delete()
        if (bkpWalFile.exists()) bkpWalFile.delete()
        if (bkpShmFile.exists()) bkpShmFile.delete()
        //checkpoint()
        try {
            dbFile.copyTo(bkpFile,true)
            if (dbWalFile.exists()) dbWalFile.copyTo(bkpWalFile,true)
            if (dbShmFile.exists()) dbShmFile.copyTo(bkpShmFile, true)
            result = 0
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     *  Restore the database and then restart the App
     */
//    fun restoreDatabase(context: Context,restart: Boolean = true) {
//        if(!File(context.getDatabasePath(THEDATABASE_DATABASE_NAME).path + THEDATABASE_DATABASE_BACKUP_SUFFIX).exists()) {
//            return
//        }
//        if (instance == null) return
//        val dbpath = INSTANCE!!.getOpenHelper().readableDatabase.path
//        val dbFile = File(dbpath)
//        val dbWalFile = File(dbFile.path + SQLITE_WALFILE_SUFFIX)
//        val dbShmFile = File(dbFile.path + SQLITE_SHMFILE_SUFFIX)
//        val bkpFile = File(dbFile.path + THEDATABASE_DATABASE_BACKUP_SUFFIX)
//        val bkpWalFile = File(bkpFile.path + SQLITE_WALFILE_SUFFIX)
//        val bkpShmFile = File(bkpFile.path + SQLITE_SHMFILE_SUFFIX)
//        try {
//            bkpFile.copyTo(dbFile, true)
//            if (bkpWalFile.exists()) bkpWalFile.copyTo(dbWalFile, true)
//            if (bkpShmFile.exists()) bkpShmFile.copyTo(dbShmFile,true)
//            checkpoint()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        if (restart) {
//            val i = context.packageManager.getLaunchIntentForPackage(context.packageName)
//            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            context.startActivity(i)
//            System.exit(0)
//        }
//    }
//    private fun checkpoint() {
//        var db = this.getOpenHelper().writableDatabase
//        db.query("PRAGMA wal_checkpoint(FULL);",null)
//        db.query("PRAGMA wal_checkpoint(TRUNCATE);",null)
//    }
}