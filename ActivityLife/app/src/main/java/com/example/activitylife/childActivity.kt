package com.example.activitylife

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class childActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_child)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("TAG", "onCreate: Child Activity")
    }
    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause: Child Activity")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "onResume: Child Activity")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "onStart: Child Activity")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "onStop: Child Activity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: Child Activity")
    }
}