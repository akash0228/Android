package com.example.livedata

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    private val factsTextView:TextView
        get() = findViewById(R.id.factsTextView)

    private val btnUpdate: Button
        get() = findViewById(R.id.btnUpdate)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.factsLiveData.observe(this,Observer{
            //code to executed when change in live data
            factsTextView.text=it
        })

        btnUpdate.setOnClickListener{
            mainViewModel.updateLiveData()
        }

    }
}