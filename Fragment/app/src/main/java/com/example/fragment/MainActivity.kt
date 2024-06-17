package com.example.fragment

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

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

        val btnFragA:Button=findViewById(R.id.btnFragA)
        val btnFragB:Button=findViewById(R.id.btnFragB)

        loadFrag(AFragment(),0)

        btnFragA.setOnClickListener{
            loadFrag(AFragment(),0)
        }

        btnFragB.setOnClickListener{
            loadFrag(BFragment(),1)
        }
    }

    fun loadFrag(frag:Fragment,flag:Int){
        val fm=supportFragmentManager
        val ft=fm.beginTransaction()

        val bundle:Bundle=Bundle()

        bundle.putString("Arg1","Akash")
        bundle.putString("Arg2","1032")

        frag.arguments=bundle

        if(flag==0) {
            //add
            ft.add(R.id.container, frag)

            //making root so that if this has come then clear all from stack this will be starting point
            fm.popBackStack("A",FragmentManager.POP_BACK_STACK_INCLUSIVE)  //includind root then inclusive else exculisive
            ft.addToBackStack("A")
        }
        else{
            ft.replace(R.id.container,frag)
            ft.addToBackStack(null) // to add to backstack so that we can come back
        }


        ft.commit()
    }

    fun callFromFrag(){
        Log.d("Tag", "callFromFrag: Hello Main")
    }
}