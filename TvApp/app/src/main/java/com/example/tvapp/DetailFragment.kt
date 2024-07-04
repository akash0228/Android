package com.example.tvapp

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

class DetailFragment(val show: Show,val parentInterface: MainActivity.MainInterface,var tab:Int) : Fragment() {
     private lateinit var watchCardView: CardView
     private lateinit var laterCardView: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun restorefocus(){
        watchCardView.requestFocus()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_detail, container, false)
        view.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN){
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //parent request upper

                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //parent request down

                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //parent request left

                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //parent request right

                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //open detail page
                    }
                    KeyEvent.KEYCODE_BACK->{
                        Log.d("BACK", " BACK")
                        parentInterface.OnKeyBack(tab)
                    }
                }
            }
            true
        }

        watchCardView=view.findViewById(R.id.watchCard)
        laterCardView=view.findViewById(R.id.laterCard)

        watchCardView.requestFocus()

        watchCardView.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN){
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //parent request upper

                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //parent request down

                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //parent request left

                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //parent request right
                        laterCardView.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //open detail page
                        parentInterface.onKeyCenterPlay(show,tab)
                    }
                    KeyEvent.KEYCODE_BACK->{
                        Log.d("BACK", " BACK")
                        parentInterface.OnKeyBack(tab)
                    }
                }
            }
            true
        }

        laterCardView.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN){
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //parent request upper

                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //parent request down

                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //parent request left
                        watchCardView.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //parent request right

                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //open detail page
                    }
                    KeyEvent.KEYCODE_BACK->{
                        Log.d("BACK", " BACK")
                        parentInterface.OnKeyBack(tab)
                    }
                }
            }
            true
        }


        return view
    }



}