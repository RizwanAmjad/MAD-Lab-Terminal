package com.rizwanamjadnov.labterminal.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class LoggingService: Service() {

    companion object{
        private const val TAG = "LOGGING SERVICE"
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "I am Started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "I am Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}