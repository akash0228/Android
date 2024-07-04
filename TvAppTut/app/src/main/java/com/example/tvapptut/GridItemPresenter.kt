package com.example.tvapptut

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.Presenter

class GridItemPresenter: Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.text_item_view,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val view:TextView=viewHolder.view as TextView
        view.text=item.toString()
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        TODO("Not yet implemented")
    }
}