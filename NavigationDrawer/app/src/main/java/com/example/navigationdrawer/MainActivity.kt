package com.example.navigationdrawer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout=findViewById(R.id.drawer)
        val navigationView:NavigationView=findViewById(R.id.naview)
        val toolbar:Toolbar=findViewById(R.id.toolbar)

        //set tool bar
        setSupportActionBar(toolbar)

        val toggle:ActionBarDrawerToggle= ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        loadFragment(AFragment())

        navigationView.setNavigationItemSelectedListener {
            val id=it.itemId

            when(id){
                R.id.optNotes -> {
                    Toast.makeText(this,"Notes",Toast.LENGTH_SHORT).show()
                }
                R.id.optHome->{
                    loadFragment(AFragment())
                }
                else -> {
                    Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show()
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

    private fun loadFragment(frag:Fragment) {
        val fm=supportFragmentManager
        val ft=fm.beginTransaction()

        ft.add(R.id.container,frag)
        ft.commit()
    }
}