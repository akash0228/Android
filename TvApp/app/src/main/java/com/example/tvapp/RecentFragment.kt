package com.example.tvapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import com.example.tvapp.databinding.FragmentRecentBinding

class RecentFragment(val parentInterface: MainActivity.MainInterface) : Fragment() {
    lateinit var binding:FragmentRecentBinding
    var lastPos=0
    lateinit var listRecentShow:List<Show>
    lateinit var gridViewAdapter:RecentGvAdapter
    lateinit var showRowViewModel:ShowRowViewModel

    val recentFragInterface=object : RecentFragInterface{
        override fun onKeyUp(pos: Int) {
            if (pos<4){
                parentInterface.onKeyUp(1)
            }
            else{
                focus(pos-4)
            }
        }

        override fun onKeyDown(pos: Int) {
            if (pos>binding.recentGrid.adapter.count-5){
                return
            }
            else{
                focus(pos+4)
            }
        }

        override fun onKeyRight(pos: Int) {
            if (pos==binding.recentGrid.adapter.count-1){
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
            parentInterface.onKeyCenter(listRecentShow.get(pos),1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridViewAdapter=RecentGvAdapter(requireContext(), mutableListOf(),recentFragInterface)
        showRowViewModel= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ShowRowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRecentBinding.inflate(inflater,container,false)

        showRowViewModel.getAllshowRows().observe(viewLifecycleOwner) { showRows ->
            if (showRows.size>0){
                listRecentShow = showRows.get(0).listShow
                gridViewAdapter = RecentGvAdapter(requireContext(),listRecentShow, recentFragInterface)
                binding.recentGrid.adapter = gridViewAdapter
            }
        }

        binding.recentGrid.adapter=gridViewAdapter
        return binding.root
    }

    fun focus(pos:Int){
        Log.d("KEY", "focus: $pos")
        if (binding.recentGrid.size>pos){
            val view=binding.recentGrid.getChildAt(pos)
            view.requestFocus()
            lastPos=pos

        }
    }

    fun restoreFocus(){
        focus(lastPos)
    }


    interface RecentFragInterface{
        fun onKeyUp(pos: Int)
        fun onKeyDown(pos: Int)
        fun onKeyRight(pos: Int)
        fun onKeyLeft(pos: Int)
        fun onKeyCenter(pos: Int)
    }

}