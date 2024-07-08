package com.example.tvapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import com.example.tvapp.databinding.FragmentSearchBinding

class SearchFragment(parentInterface: MainActivity.MainInterface) : Fragment() {
    lateinit var binding: FragmentSearchBinding
    var lastPos=0
    private lateinit var gridViewAdapter:SearchGvAdapter
    lateinit var showRowViewModel:ShowRowViewModel
    lateinit var listSearchShow:List<Show>

    val searchFragInterface=object : SearchFragInterface{
        override fun onKeyUp(pos: Int) {
            if (pos<4){
                parentInterface.onKeyUp(3)
            }
            else{
                focus(pos-4)
            }
        }

        override fun onKeyDown(pos: Int) {
            if (pos>binding.searchGrid.adapter.count-5){
                return
            }
            else{
                focus(pos+4)
            }
        }

        override fun onKeyRight(pos: Int) {
            if (pos==binding.searchGrid.adapter.count-1){
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
            parentInterface.onKeyCenter(listSearchShow.get(0),3,0,pos)
        }

        override fun onKeyBack(pos: Int) {
            parentInterface.OnKeyBack(3)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridViewAdapter= SearchGvAdapter(requireContext(), mutableListOf(),searchFragInterface)
        showRowViewModel= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ShowRowViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater,container,false)

        showRowViewModel.showList.observe(viewLifecycleOwner) { shows->
            if (shows.size>0){
                listSearchShow = shows
            }
        }

        binding.searchGrid.adapter=gridViewAdapter

        return binding.root
    }

    fun focus(pos:Int){
        if (binding.searchGrid.adapter.count>pos){
            val view=binding.searchGrid.getChildAt(pos)
            view.requestFocus()
            lastPos=pos
        }
    }
    fun restoreFocus(){
        focus(lastPos)
    }

    fun filter(query:String){
        val list=listSearchShow.filter { show -> show.title.contains(query,ignoreCase = true) }
        gridViewAdapter = SearchGvAdapter(requireContext(),list, searchFragInterface)
        binding.searchGrid.adapter = gridViewAdapter
    }

    interface SearchFragInterface{
        fun onKeyUp(pos: Int)
        fun onKeyDown(pos: Int)
        fun onKeyRight(pos: Int)
        fun onKeyLeft(pos: Int)
        fun onKeyCenter(pos: Int)

        fun onKeyBack(pos: Int)
    }
}