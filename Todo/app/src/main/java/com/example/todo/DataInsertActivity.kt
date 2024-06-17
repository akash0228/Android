package com.example.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todo.databinding.ActivityDataInsertBinding

class DataInsertActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDataInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityDataInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val type:String= intent.getStringExtra("type").toString()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(type.equals("update")){
            setTitle("Update")
            binding.title.setText(intent.getStringExtra("title"))
            binding.disp.setText(intent.getStringExtra("disp"))
            val id=intent.getIntExtra("id",0)
            binding.add.setText("update note")
            binding.add.setOnClickListener{
                Log.d("TAG", "onCreate: ${binding.title.text}")
                var intent=Intent()
                intent.putExtra("title",binding.title.text.toString())
                intent.putExtra("disp",binding.disp.text.toString())
                intent.putExtra("id",id)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }else{
            setTitle("Add More")
            binding.add.setOnClickListener{
                Log.d("TAG", "onCreate: ${binding.title.text}")
                var intent=Intent()
                intent.putExtra("title",binding.title.text.toString())
                intent.putExtra("disp",binding.disp.text.toString())
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }
}