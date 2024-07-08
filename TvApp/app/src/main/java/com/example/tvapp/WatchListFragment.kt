package com.example.tvapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import com.example.tvapp.databinding.FragmentWatchListBinding

class WatchListFragment(parentInterface: MainActivity.MainInterface) : Fragment() {

    var lastPos=0
    private lateinit var binding: FragmentWatchListBinding
    private lateinit var gridViewAdapter:WatchGvAdapter
    lateinit var showRowViewModel:ShowRowViewModel
    lateinit var listWatchListShow:List<Show>

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
            parentInterface.onKeyCenter(listWatchListShow.get(pos),2,0,pos)
        }

        override fun onKeyBack(pos: Int) {
            parentInterface.OnKeyBack(2)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridViewAdapter= WatchGvAdapter(requireContext(), mutableListOf(),watchListFragInterface)
        showRowViewModel= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ShowRowViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWatchListBinding.inflate(inflater,container,false)
        showRowViewModel.getWatchListShows().observe(viewLifecycleOwner) { shows ->
            if (shows.size>0){
                listWatchListShow = shows
                gridViewAdapter = WatchGvAdapter(requireContext(),listWatchListShow, watchListFragInterface)
                binding.watchGrid.adapter = gridViewAdapter
            }
        }
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
        fun onKeyBack(pos:Int)
    }
}