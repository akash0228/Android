package com.example.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_a, container, false)

        val txtFrag:TextView=view.findViewById(R.id.txtFrag)
        // Your Logic Here

        if(arguments!=null){
            val arg1= arguments?.getString("Arg1")
            val arg2=arguments?.getString("Arg2")

            Log.d("Values from Act","Name: $arg1  Age: $arg2")

            val ref=activity as MainActivity
            ref.callFromFrag()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = AFragment()
    }
}