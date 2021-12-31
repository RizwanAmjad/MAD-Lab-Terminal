package com.rizwanamjadnov.labterminal

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.rizwanamjadnov.labterminal.adapters.StudentAdapter
import com.rizwanamjadnov.labterminal.broadcastreceiver.AirplaneReceiver
import com.rizwanamjadnov.labterminal.databinding.ActivityMainBinding
import com.rizwanamjadnov.labterminal.models.Student
import com.rizwanamjadnov.labterminal.services.LoggingService
import com.rizwanamjadnov.labterminal.utils.NetworkResponseParser

class MainActivity : AppCompatActivity() {

    companion object{
        private const val STUDENTS_ENDPOINT = "https://run.mocky.io/v3/b8402fc5-ae31-4d98-bced-b5b3fede6d06"
    }

    lateinit var airplaneReceiver: AirplaneReceiver

    private lateinit var binding: ActivityMainBinding
    private lateinit var volleyRequestQueue: RequestQueue

    private lateinit var studentAdapter: StudentAdapter
    private var data = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPermissions()

        airplaneReceiver = AirplaneReceiver()

        studentAdapter = StudentAdapter(this, data)
        volleyRequestQueue = Volley.newRequestQueue(this)

        volleyRequestQueue.add(JsonArrayRequest(
            Request.Method.GET,
            STUDENTS_ENDPOINT,
            null, { jsonArray->
                data.addAll(NetworkResponseParser.parseStudentsList(jsonArray))
                studentAdapter.notifyItemInserted(0)
                binding.requestStatusText.visibility = View.GONE
            }, {
                val errorMessage = getString(R.string.loading_students_error)
                Snackbar.make(this, binding.root,
                    errorMessage, Snackbar.LENGTH_SHORT).show()
                binding.requestStatusText.text = errorMessage
            }
        ))

        binding.studentsRecyclerView.apply {
            adapter = studentAdapter
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    layoutManager.orientation
                )
            )
        }
        startService(Intent(this, LoggingService::class.java))
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(airplaneReceiver, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, LoggingService::class.java))
        unregisterReceiver(airplaneReceiver)
    }

    private fun getPermissions(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                ), 111)
        }
    }

}