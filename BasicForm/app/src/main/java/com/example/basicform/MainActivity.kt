package com.example.basicform

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.basicform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button.setOnClickListener{
            val name=binding.name.text
            val email=binding.email.text
            val number=binding.number.text.toString().toIntOrNull()?:0
            Log.d("TAG", "onCreate: Button Clicked")
            Log.d("TAG", "onCreate: $name $email $number")

            val sharedPreferences=getSharedPreferences("MyPref",Context.MODE_PRIVATE)

            val editor=sharedPreferences.edit();
            editor.putString("name","$name").apply();
            editor.putString("email","$email").apply();
            editor.putInt("number",number).apply();

            val intent=Intent(this, secondActivity::class.java)
            startActivity(intent)
        }
    }
}