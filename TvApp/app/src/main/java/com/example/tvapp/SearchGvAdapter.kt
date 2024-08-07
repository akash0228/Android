package com.example.tvapp

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class SearchGvAdapter(context: Context, listShow: List<Show>,val parentInterface:SearchFragment.SearchFragInterface) : ArrayAdapter<Show>(context, 0, listShow) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView: View? = convertView
        if (listItemView==null){
            listItemView=LayoutInflater.from(context).inflate(R.layout.item_card,parent,false)
        }

        val show: Show? =getItem(position)
        val idIVCard= listItemView?.findViewById<ImageView>(R.id.idIVCard)
        val idTVCard= listItemView?.findViewById<TextView>(R.id.idTVCard)

        if (idIVCard!=null){
            Glide.with(context)
                .load(show?.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(idIVCard);
        }

        if (idTVCard != null) {
            idTVCard.text= show!!.title
        }

        if (listItemView != null) {
            listItemView.setOnKeyListener { v, keyCode, event ->
                if ((event.action== KeyEvent.ACTION_DOWN)) {
                    when(keyCode){
                        KeyEvent.KEYCODE_DPAD_UP -> {
                            //will do nothing here
                            parentInterface.onKeyUp(position)
                        }
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            //send focus to mainRv
                            parentInterface.onKeyDown(position)
                        }
                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            //do nothing
                            parentInterface.onKeyLeft(position)
                        }
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            //send focus to recent tab
                            parentInterface.onKeyRight(position)
                        }
                        KeyEvent.KEYCODE_DPAD_CENTER ->{
                            //send focus to mainRv
                            parentInterface.onKeyCenter(position)
                        }
                        KeyEvent.KEYCODE_BACK ->{
                            parentInterface.onKeyBack(position)
                        }
                    }
                }
                true
            }

//            listItemView.setOnFocusChangeListener { v, hasFocus ->
//                if (hasFocus){
//                    animateScale(v,true)
//                }
//                else{
//                    animateScale(v,false)
//                }
//            }
        }

        return listItemView!!
    }

    private fun animateScale(view: View, scaleUp: Boolean) {
        val scale = if (scaleUp) 1.03f else 1.0f
        view.animate().scaleX(scale).scaleY(scale).setDuration(200).start()
    }
}