package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.databinding.EachRvBinding

class RVAdapter(diffCallback: DiffUtil.ItemCallback<Note>) :
    ListAdapter<Note, RVAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val binding:EachRvBinding
        init {
            binding=EachRvBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.each_rv,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note=getItem(position)
        holder.binding.titleRv.setText(note.title)
        holder.binding.dispRv.setText(note.disp)
    }

    fun getNote(position: Int):Note{
        return getItem(position)
    }
}