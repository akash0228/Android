package com.example.tvapp

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RowRvAdapter(val listShow: List<Show>): RecyclerView.Adapter<RowRvAdapter.ViewHolder>() {
    private lateinit var parentInterface: MainRvAdapter.MainRvInterface
    var rowPosition:Int =0
        get() = field
        set(value) {
            field=value
        }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idTvCard:TextView

        init {
            idTvCard=itemView.findViewById(R.id.idTVCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.item_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listShow.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTvCard.text=listShow.get(position).title
        Log.d("TAG", "onBindViewHolder: RowRv")
        holder.itemView.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN){
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //parent request upper
                        parentInterface.onKeyUp(position,rowPosition)
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //parent request down
                        parentInterface.onKeyDown(position,rowPosition)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                       //parent request left
                        parentInterface.onKeyLeft(position,rowPosition)
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //parent request right
                        parentInterface.onKeyRight(position,rowPosition)
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //open detail page
                        parentInterface.onKeyCenter(position,rowPosition)
                    }
                }
            }
            true
        }
    }

    fun setParentInterface(mainRvInterface: MainRvAdapter.MainRvInterface){
        parentInterface=mainRvInterface
    }
}