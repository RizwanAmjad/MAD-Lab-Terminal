package com.rizwanamjadnov.labterminal.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AirplaneReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return

        Toast.makeText(context,
            "Airplane Mode ${if (isAirplaneModeEnabled) "Enabled" else "Disabled"}",
            Toast.LENGTH_LONG).show()

    }
}