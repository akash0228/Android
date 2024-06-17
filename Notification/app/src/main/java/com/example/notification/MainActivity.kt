package com.example.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val CHANNEL:String="TEST CHANNEL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val drawable = ResourcesCompat.getDrawable(resources,R.drawable.large,null)
        val bitMapDrawable = drawable as BitmapDrawable
        val largeIcon = bitMapDrawable.bitmap

        var builder=NotificationCompat.Builder(this,CHANNEL)
            .setLargeIcon(largeIcon)
            .setSmallIcon(R.drawable.large)
            .setContentTitle("My Notification")
            .setContentText("Much Longer Text That Cannot Fit one line...")
            .setPriority(NotificationCompat.PRIORITY_HIGH)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("TAG", "onCreate: Notification")
            val channel=NotificationChannel(CHANNEL,"My Name",NotificationManager.IMPORTANCE_HIGH).apply {
                description="This is Description Of notifictaion"
            }
            val nm:NotificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
            nm.notify(100,builder.build())
        }





    }
}