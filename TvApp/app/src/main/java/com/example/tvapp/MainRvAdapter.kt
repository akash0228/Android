package com.example.tvapp

import android.icu.text.Transliterator.Position
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainRvAdapter(val listShowRow:List<ShowRow>,parentInterface: AllFragment.AllFragInterface): RecyclerView.Adapter<MainRvAdapter.ViewHolder>() {

    val mainRvInterface=object : MainRvInterface{
        override fun onKeyUp(childPos:Int,rowPosition: Int) {
            if (rowPosition==0){
                parentInterface.onKeyUp(childPos,rowPosition)
            }
            else{
                parentInterface.onKeyUp(childPos,rowPosition)
            }
        }

        override fun onKeyDown(childPos:Int,rowPosition: Int) {
            if (rowPosition==listShowRow.size-1){
                return
            }
            else{
                parentInterface.onKeyDown(childPos,rowPosition)
            }
        }

        override fun onKeyRight(childPos:Int,rowPosition: Int) {
            if (childPos==listShowRow.get(rowPosition).rowRvAdapter.listShow.size-1){
                return
            }
            else{
                parentInterface.onKeyRight(childPos,rowPosition)
            }
        }

        override fun onKeyLeft(childPos:Int,rowPosition: Int) {
            if (childPos==0){
                return
            }
            else{
                parentInterface.onKeyLeft(childPos,rowPosition)
            }
        }

        override fun onKeyCenter(childPos:Int,rowPosition: Int) {
            val show=listShowRow.get(rowPosition).rowRvAdapter.listShow.get(childPos)
            parentInterface.onKeyCenter(show)
        }



    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rowRv:RecyclerView
        var posterTitle:TextView
        fun focus(pos:Int){
            if (rowRv.size>pos){
                rowRv.findViewHolderForAdapterPosition(pos)?.itemView?.requestFocus()
            }
        }
        init {
            rowRv=itemView.findViewById(R.id.rowRv)
            posterTitle=itemView.findViewById(R.id.posterTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvAdapter.ViewHolder {
        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)

        Log.d("TAG", "onCreateViewHolder: ${listShowRow.get(0).header}")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainRvAdapter.ViewHolder, position: Int) {
        listShowRow.get(position).rowRvAdapter.setParentInterface(mainRvInterface)
        listShowRow.get(position).rowRvAdapter.rowPosition=position
        holder.rowRv.adapter=listShowRow.get(position).rowRvAdapter
        holder.rowRv.layoutManager=LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL,false)
        holder.rowRv.setHasFixedSize(true)
        holder.posterTitle.text=listShowRow.get(position).header
        Log.d("TAG", "onBindViewHolder: mainRv")
        holder.rowRv.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                holder.rowRv.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount: ${listShowRow.get(0).rowRvAdapter.listShow.get(0).title}")
        return listShowRow.size
    }

    interface MainRvInterface{
        fun onKeyUp(childPos:Int,rowPosition: Int)
        fun onKeyDown(childPos:Int,rowPosition: Int)
        fun onKeyRight(childPos:Int,rowPosition: Int)
        fun onKeyLeft(childPos:Int,rowPosition: Int)
        fun onKeyCenter(childPos:Int,rowPosition: Int)

    }


}