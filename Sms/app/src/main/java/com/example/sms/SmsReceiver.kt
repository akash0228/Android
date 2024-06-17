package com.example.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "onReceive: Msg Received")

        // Get the SMS message data from the intent
        val bundle: Bundle? = intent?.extras
        val pdus = bundle?.get("pdus") as Array<*>?

        pdus?.let {
            for (pdu in pdus) {
                val message = SmsMessage.createFromPdu(pdu as ByteArray, "3gpp")
                val mobNo = message.displayOriginatingAddress
                val msg = message.messageBody
                Log.d("MsgDetails", "Mobno: $mobNo, Msg: $msg")
            }
        }


//        val smsManager = context?.getSystemService(SmsManager::class.java)
//        smsManager?.sendTextMessage("+917903752324",null,"Helllo",null,null)
    }
}
