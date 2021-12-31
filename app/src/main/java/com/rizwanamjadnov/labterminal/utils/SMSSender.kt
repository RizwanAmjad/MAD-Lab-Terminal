package com.rizwanamjadnov.labterminal.utils

import android.telephony.SmsManager
import android.util.Log

class SMSSender {
    companion object{
        fun send(phoneNumber: String, smsText: String){
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(
                    phoneNumber,
                    "ME",
                    smsText,
                    null, null
                )
        }
    }
}