package com.example.activitylife

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        Log.d("TAG", "onCreate: Main Activity");
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause: Main Activity")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume: Main Activity")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "onStart: Main Activity")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "onStop: Main Activity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: Main Activity")
    }

    fun startChild(v:View){
        val intent = Intent(this, childActivity::class.java)
        startActivity(intent)
    }

}