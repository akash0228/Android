package com.example.tvapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.example.tvapp.databinding.FragmentWatchListBinding

class WatchListFragment(parentInterface: MainActivity.MainInterface) : Fragment() {

    val listShow1= listOf(Show("Movie 1","This is Movie 1 Description","Romance",2020,"1h 30m"),Show("Movie 2","This is Movie 2 Description","Romance",2021,"1h 35m"),Show("Movie 3","This is Movie 3 Description","Action",2005,"1h 40m"),Show("Movie 4","This is Movie 4 Description","Thriller",2023,"2h 30m"),Show("Movie 5","This is Movie 5 Description","Horror",2018,"1h 23m"))
    var lastPos=0
    private lateinit var binding: FragmentWatchListBinding

    val watchListFragInterface=object : WatchListFragInterface {
        override fun onKeyUp(pos: Int) {
            if (pos<4){
                parentInterface.onKeyUp(2)
            }
            else{
                focus(pos-4)
            }
        }

        override fun onKeyDown(pos: Int) {
            if (pos>binding.watchGrid.adapter.count-5){
                return
            }
            else{
                focus(pos+4)
            }
        }

        override fun onKeyRight(pos: Int) {
            if (pos==binding.watchGrid.adapter.count-1){
                return
            }
            else{
                focus(pos+1)
            }
        }

        override fun onKeyLeft(pos: Int) {
            if (pos==0){
                return
            }
            else{
                focus(pos-1)
            }
        }

        override fun onKeyCenter(pos: Int) {
            parentInterface.onKeyCenter(listShow1.get(pos),2)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWatchListBinding.inflate(inflater,container,false)
        val gridViewAdapter=WatchGvAdapter(requireContext(),listShow1,watchListFragInterface)
        binding.watchGrid.adapter=gridViewAdapter
        return binding.root
    }

    fun focus(pos:Int){
        if (binding.watchGrid.size>pos){
            val view=binding.watchGrid.getChildAt(pos)
            view.requestFocus()
            lastPos=pos
        }

    }

    fun restoreFocus(){
        focus(lastPos)
    }
    interface WatchListFragInterface{
        fun onKeyUp(pos: Int)
        fun onKeyDown(pos: Int)
        fun onKeyRight(pos: Int)
        fun onKeyLeft(pos: Int)
        fun onKeyCenter(pos: Int)
    }
}