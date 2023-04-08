//package com.example.sandoghRasis.ui.database
//
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.os.Environment
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.example.exportdatabase.test.DatabaseExporter
//import com.example.sandoghRasis.databinding.FragmentBackupBinding
//import com.example.sandoghRasis.db.RasisDataBase
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.io.OutputStream
//import java.nio.channels.FileChannel
//
//
//@AndroidEntryPoint
//class BackupFragment : Fragment() {
//    private lateinit var binding: FragmentBackupBinding
//    private lateinit var viewModel: BackupViewModel
//
//    private lateinit var myDatabase: RasisDataBase
//    private lateinit var databaseExporter: DatabaseExporter
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentBackupBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        // Request permission to write to external storage if not already granted
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                1
//            )
//        }
//
//        myDatabase = RasisDataBase.getDataClient(requireContext())
//        databaseExporter = DatabaseExporter(requireContext(), "DATABASE")
//
//        // Retrieve data from Room database
//        GlobalScope.launch {
//            val myEntities = myDatabase.dao().getAllMenu()
//            // Do something with the entities here (e.g. display them in a RecyclerView)
//        }
//
//        // Export database to a file when export button is clicked
//        binding.btnExport.setOnClickListener { exportDatabase() }
//    }
//
//    private fun exportDatabase() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            // Export database to a file
//            backupDatabase()
////            databaseExporter.exportDatabase()
//        } else {
//            // Permission not granted, request it again
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                1
//            )
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            // Permission granted, export database to a file
//            databaseExporter.exportDatabase()
//        }
//    }
//
//
//    fun exportDatabase2() {
//        try {
//            val sd: File = Environment.getExternalStorageDirectory()
//            val data: File = Environment.getDataDirectory()
//            val currentDBPath = "//data//com.example.sandoghRasis//databases//DATABASE"
//            val backupDBPath = "user_database.db"
//            val currentDB = File(data, currentDBPath)
//            val backupDB = File(sd, backupDBPath)
//            val src: FileChannel = FileInputStream(currentDB).channel
//            val dst: FileChannel = FileOutputStream(backupDB).channel
//            dst.transferFrom(src, 0, src.size())
//            src.close()
//            dst.close()
//            Toast.makeText(
//                requireContext(),
//              "",
//                Toast.LENGTH_SHORT
//            ).show()
//        } catch (e: Exception) {
//            Toast.makeText(
//                requireContext(),
//                "Main\"",
//                Toast.LENGTH_SHORT
//            ).show()
//            Log.d("Main", e.toString())
//        }
//    }
//
//    fun backupDatabase() {
//        //Open your local db as the input stream
//        val currentDBPath = "//data//com.example.sandoghRasis//databases//user_database"
//        val dbFile = File(currentDBPath)
//        val fis = FileInputStream(dbFile)
//        val outFileName = Environment.getExternalStorageDirectory().toString() + "/Alamrs"
//        //Open the empty db as the output stream
//        val output: OutputStream = FileOutputStream(outFileName)
//        //transfer bytes from the inputfile to the outputfile
//        val buffer = ByteArray(1024)
//        var length: Int
//        while (fis.read(buffer).also { length = it } > 0) {
//            output.write(buffer, 0, length)
//        }
//        //Close the streams
//        output.flush()
//        output.close()
//        fis.close()
//    }
//}