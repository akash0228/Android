package com.example.bottomnavigation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        var bnView:BottomNavigationView=this.findViewById(R.id.bnView)

        bnView.setOnItemSelectedListener{
            val id=it.itemId
            when(id){
                R.id.nav_search -> {
                    loadFrag(searchFragment(),0)
                }
                R.id.nav_util ->{
                    loadFrag(utilFragment(),0)
                }
                R.id.nav_contact->{
                    loadFrag(contactFragment(),0)
                }
                R.id.nav_profile->{
                    loadFrag(profileFragment(),0)
                }
                else ->{
                    loadFrag(homeFragment(),1)
                }
            }
            true
        }

        bnView.selectedItemId=R.id.nav_home
    }

    fun loadFrag(frag:Fragment,flag:Int){
        val fm: FragmentManager = supportFragmentManager
        val ft=fm.beginTransaction()
        if(flag==1)
         ft.add(R.id.container,frag)
        else
            ft.replace(R.id.container,frag)
        ft.commit()
    }
}