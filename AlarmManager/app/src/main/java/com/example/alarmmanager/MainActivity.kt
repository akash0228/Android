package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText:EditText=findViewById(R.id.edtTime)
        val btn:Button= findViewById(R.id.btnSet)
        val alarmManager:AlarmManager= getSystemService(ALARM_SERVICE) as AlarmManager

        btn.setOnClickListener{
            val time:Int=editText.text.toString().toInt()
            val triggerTime:Long=System.currentTimeMillis()+(time*1000)

            val iBroadCast=Intent(this,MyReceiver::class.java)
            val pendingIntent:PendingIntent=PendingIntent.getBroadcast(this,100, iBroadCast, PendingIntent.FLAG_MUTABLE)

            alarmManager.set(AlarmManager.RTC_WAKEUP,triggerTime,pendingIntent)
        }


    }
}