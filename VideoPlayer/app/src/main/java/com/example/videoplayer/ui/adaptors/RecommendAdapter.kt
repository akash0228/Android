package com.example.videoplayer.ui.adaptors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.videoplayer.R
import com.example.videoplayer.data.entities.Video
import com.example.videoplayer.ui.views.PlayerActivity
import java.util.Objects

class RecommendAdapter(val context: Context,val listVideo:List<Video>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater=LayoutInflater.from(context)
        val itemView=inflater.inflate(R.layout.recommend_item,container,false)
        val titleRecommend=itemView.findViewById<TextView>(R.id.titleRecommend)
        titleRecommend.setText(listVideo.get(position).title)
        Objects.requireNonNull(container).addView(itemView)

        itemView.setOnClickListener{
            val currVideo=listVideo.get(position)
            val intent= Intent(context, PlayerActivity::class.java)
            intent.putExtra("videoTitle",currVideo.title)
            intent.putExtra("videoURL",currVideo.url)
            intent.putExtra("videoLyrics",currVideo.lyrics)
            itemView.context.startActivity(intent)
        }

        return itemView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
    }

    override fun getCount(): Int {
        return listVideo.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}