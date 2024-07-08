package com.example.tvapp

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RowRvAdapter(val listShow: List<Show>): RecyclerView.Adapter<RowRvAdapter.ViewHolder>() {
    private lateinit var parentInterface: MainRvAdapter.MainRvInterface
    var rowPosition:Int =0
        get() = field
        set(value) {
            field=value
        }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idTvCard:TextView
        var idIvCard:ImageView

        init {
            idTvCard=itemView.findViewById(R.id.idTVCard)
            idIvCard=itemView.findViewById(R.id.idIVCard)
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

//        val url=listShow.get(position).imageUrl
//        Log.d("IMAGE", "$url")
//            Glide.with(holder.itemView.context)
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.idIvCard);

        holder.itemView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                animateScale(v,true)
            }
            else{
                animateScale(v,false)
            }
        }

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

    private fun animateScale(view: View, scaleUp: Boolean) {
        val scale = if (scaleUp) 1.1f else 1.0f
        view.animate().scaleX(scale).scaleY(scale).setDuration(200).start()
    }

    fun setParentInterface(mainRvInterface: MainRvAdapter.MainRvInterface){
        parentInterface=mainRvInterface
    }
}