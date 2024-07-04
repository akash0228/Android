package com.example.tvapptut

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter

class CardPresenter : Presenter() {

    private lateinit var mContext: Context

    class ViewHolder(view: View?) : Presenter.ViewHolder(view){
        private lateinit var mMovie: Movie
        private var mCardView: ImageCardView=view as ImageCardView
        private var mDefaultCardImage:Drawable= view?.context?.getDrawable(R.drawable.movie)!!

        fun setMovie(m:Movie){
            mMovie=m
        }

        fun getMovie():Movie{
            return mMovie
        }

        fun getCardView():ImageCardView{
            return mCardView
        }

        fun getDefaultCardImage():Drawable{
            return mDefaultCardImage
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        mContext=parent.context
//        val cardView = ImageCardView(mContext).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            focusable = View.FOCUSABLE
//            isFocusableInTouchMode = true
//            setBackgroundColor(mContext.getColor(R.color.fastlane_background))
//        }
        val cardView=LayoutInflater.from(mContext).inflate(R.layout.imageview_item,parent,false) as ImageCardView

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any?) {
        val movie:Movie=item as Movie
        val myViewHolder=viewHolder as ViewHolder
        myViewHolder.setMovie(movie)

        myViewHolder.getCardView().titleText=movie.title
        myViewHolder.getCardView().contentText=movie.studio
        myViewHolder.getCardView().setMainImageDimensions(313,176)
        myViewHolder.getCardView().mainImage = myViewHolder.getDefaultCardImage()
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        TODO("Not yet implemented")
    }

}