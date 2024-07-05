package com.example.tvapp

import android.inputmethodservice.Keyboard.Row
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapp.databinding.FragmentAllBinding

class AllFragment(parentInterface: MainActivity.MainInterface) : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private lateinit var mainAdapter: MainRvAdapter
    private lateinit var showRowViewModel: ShowRowViewModel
    private lateinit var listShowRow:List<ShowRow>

    var lastRow=0
    var lastCol=0


    val allFragInterface =object : AllFragInterface {
        override fun onKeyUp(childPos: Int,rowPosition: Int) {
            if (rowPosition==0){
                parentInterface.onKeyUp(0)
            }
            else{
                focus(rowPosition-1,0)
            }
        }

        override fun onKeyDown(childPos: Int, rowPosition: Int) {
            Log.d("KEY", "onKeyDown: ${rowPosition}")
            focus(0,rowPosition+1)
        }

        override fun onKeyLeft(childPos: Int, rowPosition: Int) {
            focus(childPos-1,rowPosition)
        }

        override fun onKeyRight(childPos: Int, rowPosition: Int) {
            focus(childPos+1,rowPosition)
        }

        override fun onKeyCenter(show: Show) {
            parentInterface.onKeyCenter(show,0)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainAdapter=MainRvAdapter(mutableListOf(),allFragInterface)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("lastRow",lastRow)
        outState.putInt("lastCol",lastCol)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAllBinding.inflate(inflater,container,false)
        showRowViewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ShowRowViewModel::class.java)

        showRowViewModel.getAllshowRows().observe(viewLifecycleOwner) { showRows ->
            listShowRow = showRows
            mainAdapter = MainRvAdapter(listShowRow, allFragInterface)
            binding.allRv.adapter = mainAdapter
        }

        binding.allRv.layoutManager= LinearLayoutManager(requireContext())
        binding.allRv.setHasFixedSize(true)

        binding.allRv.adapter=mainAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "View Created ")
    }


    fun focus(childPos:Int,rowPos:Int){
        if (binding.allRv.size>rowPos){
            val holder=binding.allRv.findViewHolderForAdapterPosition(rowPos) as MainRvAdapter.ViewHolder
            holder.focus(childPos)
            lastRow=rowPos
            lastCol=childPos

            changePreview(listShowRow.get(rowPos).listShow.get(childPos))

        }
    }

    fun changePreview(show: Show){
        binding.posterTitle.text=show.title
        binding.posterCategory.text="${show.category} * ${show.year} * ${show.Duration}"
        binding.posterDescription.text=show.Description
    }

    fun restoreFocus(){
        Log.d("BACK", "RESTORE $lastCol $lastRow")
        focus(lastCol,lastRow)
    }

    interface AllFragInterface{
        fun onKeyUp(childPos: Int,rowPosition:Int)
        fun onKeyDown(childPos:Int,rowPosition: Int)
        fun onKeyLeft(childPos:Int,rowPosition: Int)
        fun onKeyRight(childPos:Int,rowPosition: Int)

        fun onKeyCenter(show: Show)

    }

}