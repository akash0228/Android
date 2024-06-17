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
import com.squareup.picasso.Picasso

class PicasoDemo : AppCompatActivity() {
    private lateinit var  imageview_two: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_picaso_demo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageview_two=findViewById(R.id.imageview_two)
    }
    fun openImageUsingPicaso(view: View){
        val url="https://prod-img.thesouledstore.com/public/theSoul/uploads/catalog/product/1687842433_3385347.jpg?format=webp&w=480&dpr=1.0"
        //same methods as glide
        Picasso.get().load(url).into(imageview_two)
    }
}