package com.example.videoplayer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.math.log

class RecyclerVideoAdapter(val context:Context,val listVideo:List<Video>,val videoViewModel: VideoViewModel):RecyclerView.Adapter<RecyclerVideoAdapter.ViewHolder>(){

    public class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var titleRv:TextView
        var llRow:LinearLayout
        var favRv:ImageView
        init {
            titleRv=itemView.findViewById(R.id.titleRv)
            llRow=itemView.findViewById(R.id.llRow)
            favRv=itemView.findViewById(R.id.favRv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            Log.d("TAG", "onCreateViewHolder: ")

        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false)

        val viewHolder=ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return listVideo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleRv.text=listVideo.get(position).title

        if(listVideo.get(position).isFavourite){
            holder.itemView.findViewById<ImageView>(R.id.favRv).setColorFilter(Color.YELLOW)
        }


        holder.favRv.setOnClickListener{
            val currVideo=listVideo.get(position)
            if(currVideo.isFavourite){
                currVideo.isFavourite=false
                videoViewModel.update(currVideo)
                val favRv:ImageView=holder.itemView.findViewById(R.id.favRv)
                favRv.setColorFilter(Color.WHITE)
                notifyItemChanged(position)
            }
            else{
                currVideo.isFavourite=true
                videoViewModel.update(currVideo)
                val favRv:ImageView=holder.itemView.findViewById(R.id.favRv)
                favRv.setColorFilter(Color.YELLOW)
                notifyItemChanged(position)
            }
        }

        holder.llRow.setOnClickListener{
            val currVideo=listVideo.get(position)
            val intent= Intent(context,PlayerActivity::class.java)
            intent.putExtra("videoTitle",currVideo.title)
            intent.putExtra("videoURL",currVideo.url)
            intent.putExtra("videoLyrics",currVideo.lyrics)
            holder.itemView.context.startActivity(intent)
        }

        holder.llRow.setOnLongClickListener{
            Log.d("TAG","LONG KEY PRESSED")
            val dialog= Dialog(context)
            dialog.setContentView(R.layout.video_preview_lay)
            val title:TextView=dialog.findViewById(R.id.videoTitle)
            val videoImage:ImageView=dialog.findViewById(R.id.videoImage)

            val currVideo=listVideo.get(position)

            title.text=currVideo.title

            Glide.with(context)
                .load(currVideo.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(videoImage);

            dialog.show()
            true
        }

    }

}