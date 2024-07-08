package com.example.tvapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
                binding.allRv.smoothScrollToPosition(rowPosition-1)

            }
        }

        override fun onKeyDown(childPos: Int, rowPosition: Int) {
            Log.d("KEY", "onKeyDown: ${rowPosition}")
            focus(0,rowPosition+1)

            if (rowPosition+1 < mainAdapter.getItemCount()) {
                binding.allRv.smoothScrollToPosition(rowPosition+1)
            }
        }

        override fun onKeyLeft(childPos: Int, rowPosition: Int) {
            focus(childPos-1,rowPosition)
        }

        override fun onKeyRight(childPos: Int, rowPosition: Int) {
            focus(childPos+1,rowPosition)
        }

        override fun onKeyCenter(show: Show,rowPosition: Int, childPos: Int) {
            parentInterface.onKeyCenter(show,0,rowPosition, childPos)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAllBinding.inflate(inflater,container,false)
        showRowViewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ShowRowViewModel::class.java)

        showRowViewModel.showList.observe(viewLifecycleOwner) { showRows ->
            Log.d("ALL", "onCreateView: ${showRows.size}")
           loadData(showRows)
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

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadData(list:List<Show>){
//        val list=showRowViewModel.getAllShowRow().value
        val hashMap=HashMap<String,ArrayList<Show>>()
        list.forEach { show ->
            val listShow: ArrayList<Show> = hashMap.getOrPut(show.Header) { ArrayList() }
            listShow.add(show)
        }

        val showRows= hashMap.entries.map { (header, listShow) ->
            ShowRow(header, listShow)
        }


        if (showRows!=null) {
            listShowRow = showRows
            mainAdapter = MainRvAdapter(listShowRow, allFragInterface)
            binding.allRv.adapter = mainAdapter
        }
    }

    fun changePreview(show: Show){
        binding.posterTitle.text=show.title
        binding.posterCategory.text="${show.category} * ${show.year} * ${show.Duration}"
        binding.posterDescription.text=show.Description
        val url=show.imageUrl
        Glide.with(requireContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.mainPoster);
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
        fun onKeyCenter(show: Show, rowPosition: Int, childPos: Int)

    }

}