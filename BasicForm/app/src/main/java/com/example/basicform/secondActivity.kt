package com.example.basicform

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.basicform.databinding.ActivityMainBinding
import com.example.basicform.databinding.ActivitySecondBinding

class secondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySecondBinding.inflate(layoutInflater);
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences=getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val name=sharedPreferences.getString("name","Name")
        val email=sharedPreferences.getString("email","Email")
        val number=sharedPreferences.getInt("number",0)
        Log.d("TAG", "onCreate: $name")
        binding.detail.text="Hii $name Your email is $email and your number is $number"
    }
}