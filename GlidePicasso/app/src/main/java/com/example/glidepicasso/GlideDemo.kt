package com.example.glidepicasso

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide

class GlideDemo : AppCompatActivity() {
    private lateinit var  imageview_one: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_glide_demo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageview_one=findViewById(R.id.imageview_one)
    }

    fun openImageUsingGlide(view: View){
        val url="https://prod-img.thesouledstore.com/public/theSoul/uploads/catalog/product/1687842433_3385347.jpg?format=webp&w=480&dpr=1.0"

       // Placeholder will be loaded until image is loaded
        //in case error error one will be displayed
        Glide.with(this).load(url).error(R.drawable.baseline_cloud_off_24).transition(GenericTransitionOptions.with(android.R.anim.slide_in_left)).placeholder(R.drawable.baseline_backup_24).into(imageview_one)
    }
}